package com.digginroom.digginroom.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _uiState: MutableLiveData<LoginUiState> = MutableLiveData()
    val uiState: LiveData<LoginUiState>
        get() = _uiState

    fun login(account: AccountModel) {
        _uiState.value = LoginUiState.Loading

        viewModelScope.launch {
            accountRepository.postLogIn(
                id = account.id,
                password = account.password
            ).onSuccess { loginResult ->
                _uiState.value = LoginUiState.Succeed.from(loginResult.hasSurveyed)
            }.onFailure {
                _uiState.value = LoginUiState.Failed
            }
        }
    }

    fun startGoogleLogin() {
        _googleLoginEvent.call()
    }

    fun login(idToken: String) {
        _uiState.value = LoginUiState.Loading

        viewModelScope.launch {
            accountRepository.postLogin(idToken)
                .onSuccess { loginResult ->
                    _uiState.value = LoginUiState.Succeed.from(loginResult.hasSurveyed)
                }.onFailure {
                    _uiState.value = LoginUiState.Failed
                }
        }
    }
}
