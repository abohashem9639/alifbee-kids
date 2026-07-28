package com.alifbee.kidsdemo.ui.screens.matchpairs.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.alifbee.kidsdemo.data.repository.MatchPairsRepository
import com.alifbee.kidsdemo.data.result.AppError
import com.alifbee.kidsdemo.ui.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MatchPairsViewModel @Inject constructor(
    private val matchPairsRepository: MatchPairsRepository,
) : BaseViewModel<MatchPairsContract.Event, MatchPairsContract.State, MatchPairsContract.Effect>(
    initialState = MatchPairsContract.State()
) {

    init {
        loadQuestion()
    }

    override fun handleEvent(event: MatchPairsContract.Event) {
        when (event) {
            is MatchPairsContract.Event.OnCardDropped -> onCardDropped(event.cardId, event.slotIndex)
            MatchPairsContract.Event.OnCheckClicked -> checkAnswers()
        }
    }

    private fun loadQuestion() {
        setState { copy(fetchState = MatchPairsContract.FetchState.IsLoading(true)) }
        viewModelScope.launch {
            runCatching { matchPairsRepository.getMatchPairs() }
                .onSuccess { question ->
                    setState {
                        copy(
                            questionText = question.questionText,
                            cardsA = question.groupA,
                            slotsB = question.groupB,
                            assignments = emptyMap(),
                            result = null,
                            fetchState = MatchPairsContract.FetchState.LoadData(),
                        )
                    }
                }
                .onFailure {
                    setState {
                        copy(fetchState = MatchPairsContract.FetchState.LoadData(error = AppError.General()))
                    }
                }
        }
    }

    private fun onCardDropped(cardId: Int, slotIndex: Int?) {
        if (uiState.value.result != null) return
        setState {
            val updated = assignments.toMutableMap()
            val previousSlot = updated[cardId]
            if (slotIndex == null) {
                updated.remove(cardId)
            } else {
                val occupant = updated.entries.firstOrNull { it.value == slotIndex && it.key != cardId }?.key
                if (occupant != null) {
                    if (previousSlot != null) updated[occupant] = previousSlot else updated.remove(occupant)
                }
                updated[cardId] = slotIndex
            }
            copy(assignments = updated)
        }
    }

    private fun checkAnswers() {
        val state = uiState.value
        if (!state.allPlaced || state.result != null) return
        val result = state.slotsB.mapIndexed { index, slot ->
            val cardId = state.assignments.entries.first { it.value == index }.key
            index to (cardId == slot.id)
        }.toMap()
        setState { copy(result = result) }
        if (result.values.all { it }) return
        viewModelScope.launch {
            delay(1200L)
            setState {
                copy(
                    assignments = assignments.filter { (_, slot) -> result[slot] == true },
                    result = null,
                )
            }
        }
    }
}
