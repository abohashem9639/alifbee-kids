package com.alifbee.kidsdemo.ui.screens.login.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.alifbee.kidsdemo.data.datastore.DataStoreManager
import com.alifbee.kidsdemo.data.repository.AuthRepository
import com.alifbee.kidsdemo.data.result.onFailure
import com.alifbee.kidsdemo.data.result.onSuccess
import com.alifbee.kidsdemo.ui.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreManager: DataStoreManager,
) : BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>(
    initialState = LoginContract.State()
) {

    override fun handleEvent(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.OnLoginClicked -> signIn(event.email, event.password)
        }
    }

    private fun signIn(email: String, password: String) {
        setState { copy(fetchState = LoginContract.FetchState.IsLoading(true)) }
        viewModelScope.launch {
            authRepository.signIn(email, password)
                .onSuccess { session ->
                    dataStoreManager.setUserSession(session)
                    setState { copy(fetchState = LoginContract.FetchState.LoadData()) }
                    setEffect { LoginContract.Effect.SignInSuccessNavigate }
                }
                .onFailure { error ->
                    setState { copy(fetchState = LoginContract.FetchState.LoadData(error = error)) }
                }
        }
    }
}
