package com.digginroom.digginroom.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.livedata.NonNullMutableLiveData
import com.digginroom.digginroom.model.AccountModel
import com.digginroom.digginroom.repository.AccountRepository
import com.digginroom.digginroom.util.SingleLiveEvent
import kotlinx.coroutines.launch

class LoginViewModel(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _googleLoginEvent: SingleLiveEvent<Any> = SingleLiveEvent()

    val googleLoginEvent: LiveData<Any>
        get() = _googleLoginEvent

    private val _uiState: NonNullMutableLiveData<LoginUiState> = NonNullMutableLiveData(
        LoginUiState(
            accountModel = AccountModel(),
            loginState = LoginState.InProgress
        )
    )

    val uiState: LiveData<LoginUiState>
        get() = _uiState

    fun login(account: AccountModel) {
        _uiState.value = _uiState.value.changeState(LoginState.Loading)

        viewModelScope.launch {
            accountRepository.postLogIn(
                id = account.id,
                password = account.password
            ).onSuccess { loginResult ->
                _uiState.value = _uiState.value.changeState(
                    LoginState.Succeed.from(loginResult.hasSurveyed)
                )
            }.onFailure {
                _uiState.value = _uiState.value.changeState(LoginState.Failed)
            }
        }
    }

    fun startGoogleLogin() {
        _googleLoginEvent.call()
    }

    fun googleLogin(idToken: String) {
        _uiState.value = _uiState.value.changeState(LoginState.Loading)

        viewModelScope.launch {
            accountRepository.postLogin(idToken)
                .onSuccess { loginResult ->
                    _uiState.value = _uiState.value.changeState(
                        LoginState.Succeed.from(loginResult.hasSurveyed)
                    )
                }.onFailure {
                    _uiState.value = _uiState.value.changeState(LoginState.Failed)
                }
        }
    }
}
