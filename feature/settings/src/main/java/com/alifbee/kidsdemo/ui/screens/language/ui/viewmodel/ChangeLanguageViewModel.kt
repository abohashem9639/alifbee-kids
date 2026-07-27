package com.alifbee.kidsdemo.ui.screens.language.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.alifbee.kidsdemo.core.designsystem.R
import com.alifbee.kidsdemo.data.datastore.DataStoreManager
import com.alifbee.kidsdemo.data.model.AppLanguage
import com.alifbee.kidsdemo.data.repository.ReviewRepository
import com.alifbee.kidsdemo.data.repository.SyllabusRepository
import com.alifbee.kidsdemo.data.repository.UserRepository
import com.alifbee.kidsdemo.ui.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ChangeLanguageViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val syllabusRepository: SyllabusRepository,
    private val reviewRepository: ReviewRepository,
    private val dataStoreManager: DataStoreManager,
) : BaseViewModel<ChangeLanguageContract.Event, ChangeLanguageContract.State, ChangeLanguageContract.Effect>(
    initialState = ChangeLanguageContract.State()
) {

    private val availableLanguages = listOf(
        AppLanguage(code = "en", name = "English", flagRes = R.drawable.img_us_flag),
        AppLanguage(code = "tr", name = "Türkçe", flagRes = R.drawable.ic_tr_flag),
        AppLanguage(code = "ar", name = "العربية", flagRes = R.drawable.ic_ar_flag),
    )

    init {
        setState { copy(languages = availableLanguages, selectedCode = availableLanguages.first().code) }
        viewModelScope.launch {
            val language = dataStoreManager.appLanguage.first()
            val theme = dataStoreManager.appThemeMode.first()
            setState {
                copy(
                    selectedCode = availableLanguages.firstOrNull { it.code == language }?.code ?: selectedCode,
                    selectedTheme = theme,
                )
            }
        }
    }

    override fun handleEvent(event: ChangeLanguageContract.Event) {
        when (event) {
            is ChangeLanguageContract.Event.OnLanguageSelected -> {
                setState { copy(selectedCode = event.code) }
            }
            is ChangeLanguageContract.Event.OnThemeSelected -> {
                setState { copy(selectedTheme = event.mode) }
            }
            ChangeLanguageContract.Event.OnDoneClicked -> applyChanges()
        }
    }

    private fun applyChanges() {
        if (uiState.value.isUpdating) return
        val code = uiState.value.selectedCode
        val theme = uiState.value.selectedTheme
        setState { copy(isUpdating = true) }
        viewModelScope.launch {
            dataStoreManager.setAppLanguage(code)
            dataStoreManager.setAppThemeMode(theme)
            userRepository.updateLearningLanguage(code)
            runCatching { syllabusRepository.refreshSyllabus() }
            runCatching { reviewRepository.refreshReviews() }
            setState { copy(isUpdating = false) }
            setEffect { ChangeLanguageContract.Effect.NavigateBack }
        }
    }
}
