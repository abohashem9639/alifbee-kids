package com.alifbee.kidsdemo.ui.screens.splash.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.alifbee.kidsdemo.data.datastore.DataStoreManager
import com.alifbee.kidsdemo.data.repository.ReviewRepository
import com.alifbee.kidsdemo.data.repository.SyllabusRepository
import com.alifbee.kidsdemo.ui.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SplashViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val syllabusRepository: SyllabusRepository,
    private val reviewRepository: ReviewRepository,
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
            val loggedIn = dataStoreManager.userLoggedIn.first()
            if (loggedIn) {
                listOf(
                    async { runCatching { syllabusRepository.refreshSyllabus() } },
                    async { runCatching { reviewRepository.refreshReviews() } },
                ).awaitAll()
            }
            setState { copy(isLoading = false) }
            setEffect {
                if (loggedIn) SplashContract.Effect.NavigateToHome
                else SplashContract.Effect.NavigateToLogin
            }
        }
    }
}
