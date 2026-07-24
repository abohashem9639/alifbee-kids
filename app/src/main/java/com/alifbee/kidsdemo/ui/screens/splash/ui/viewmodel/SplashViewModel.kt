package com.alifbee.kidsdemo.ui.screens.splash.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.alifbee.kidsdemo.data.datastore.DataStoreManager
import com.alifbee.kidsdemo.ui.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SplashViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
) : BaseViewModel<SplashContract.Event, SplashContract.State, SplashContract.Effect>(
    initialState = SplashContract.State()
) {

    init {
        startSplashTimer()
    }

    override fun handleEvent(event: SplashContract.Event) = Unit

    private fun startSplashTimer() {
        viewModelScope.launch {
            delay(1000L)
            setState { copy(isLoading = false) }
            val loggedIn = dataStoreManager.userLoggedIn.first()
            setEffect {
                if (loggedIn) {
                    SplashContract.Effect.NavigateToHome
                } else {
                    SplashContract.Effect.NavigateToLogin
                }
            }
        }
    }
}
