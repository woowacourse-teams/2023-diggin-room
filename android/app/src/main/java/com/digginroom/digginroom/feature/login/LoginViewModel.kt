package com.digginroom.digginroom.feature.login

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.model.AccountModel
import com.digginroom.digginroom.repository.AccountRepository
import com.dygames.di.annotation.NotCaching
import kotlinx.coroutines.launch

@NotCaching
class LoginViewModel @Keep constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

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
        _uiState.value = LoginUiState.InProgress.Google
    }

    fun startKakaoLogin() {
        _uiState.value = LoginUiState.InProgress.KaKao
    }

    fun socialLogin(idToken: String) {
        _uiState.value = LoginUiState.Loading

        viewModelScope.launch {
            accountRepository.postSocialLogin(idToken)
                .onSuccess { loginResult ->
                    _uiState.value = LoginUiState.Succeed.from(loginResult.hasSurveyed)
                }.onFailure {
                    _uiState.value = (LoginUiState.Failed)
                }
        }
    }
}
