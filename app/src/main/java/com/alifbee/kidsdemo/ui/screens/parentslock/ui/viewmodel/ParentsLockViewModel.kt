package com.alifbee.kidsdemo.ui.screens.parentslock.ui.viewmodel

import android.content.Context
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.ui.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
internal class ParentsLockViewModel @Inject constructor(
    @ApplicationContext context: Context,
) : BaseViewModel<ParentsLockContract.Event, ParentsLockContract.State, ParentsLockContract.Effect>(
    initialState = ParentsLockContract.State()
) {

    private val code = List(3) { Random.nextInt(1, 10) }

    init {
        val numberWords = context.resources.getStringArray(R.array.number_words)
        setState { copy(codeWords = code.joinToString(", ") { numberWords[it] }) }
    }

    override fun handleEvent(event: ParentsLockContract.Event) {
        when (event) {
            is ParentsLockContract.Event.OnDigitClicked -> onDigit(event.digit)
            ParentsLockContract.Event.OnDeleteClicked -> onDelete()
        }
    }

    private fun onDigit(digit: Int) {
        val current = uiState.value.entered
        if (current.size >= code.size) return
        val updated = current + digit
        if (updated.size < code.size) {
            setState { copy(entered = updated) }
        } else {
            if (updated == code) setEffect { ParentsLockContract.Effect.NavigateToMoreApps }
            setState { copy(entered = emptyList()) }
        }
    }

    private fun onDelete() {
        val current = uiState.value.entered
        if (current.isNotEmpty()) setState { copy(entered = current.dropLast(1)) }
    }
}
