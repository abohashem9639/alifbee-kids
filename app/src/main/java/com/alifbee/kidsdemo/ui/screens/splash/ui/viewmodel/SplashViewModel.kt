package com.alifbee.kidsdemo.ui.screens.splash.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.alifbee.kidsdemo.ui.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SplashViewModel @Inject constructor() :
    BaseViewModel<SplashContract.Event, SplashContract.State, SplashContract.Effect>(
        initialState = SplashContract.State()
    ) {

    init {
        startSplashTimer()
    }

    override fun handleEvent(event: SplashContract.Event) = Unit

    private fun startSplashTimer() {
        viewModelScope.launch {
            delay(3000L)
            setState { copy(isLoading = false) }
            setEffect { SplashContract.Effect.NavigateToHome }
        }
    }
}
