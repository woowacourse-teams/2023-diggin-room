package com.digginroom.digginroom.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.model.AccountModel
import com.digginroom.digginroom.repository.AccountRepository
import com.digginroom.digginroom.util.MutableSingleLiveData
import com.digginroom.digginroom.util.SingleLiveData
import kotlinx.coroutines.launch

class LoginViewModel(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _uiState: MutableSingleLiveData<LoginUiState> =
        MutableSingleLiveData(LoginUiState.InProgress.General)
    val uiState: SingleLiveData<LoginUiState>
        get() = _uiState

    fun login(account: AccountModel) {
        _uiState.setValue(LoginUiState.Loading)

        viewModelScope.launch {
            accountRepository.postLogIn(
                id = account.id,
                password = account.password
            ).onSuccess { loginResult ->
                _uiState.setValue(LoginUiState.Succeed.from(loginResult.hasSurveyed))
            }.onFailure {
                _uiState.setValue(LoginUiState.Failed)
            }
        }
    }

    fun startGoogleLogin() {
        _uiState.setValue(LoginUiState.InProgress.Google)
    }

    fun startKakaoLogin() {
        _uiState.setValue(LoginUiState.InProgress.KaKao)
    }

    fun googleLogin(idToken: String) {
        _uiState.setValue(LoginUiState.Loading)

        viewModelScope.launch {
            accountRepository.postGoogleLogin(idToken)
                .onSuccess { loginResult ->
                    _uiState.setValue(LoginUiState.Succeed.from(loginResult.hasSurveyed))
                }.onFailure {
                    _uiState.setValue(LoginUiState.Failed)
                }
        }
    }

    fun kakaoLogin(idToken: String) {
        _uiState.setValue(LoginUiState.Loading)

        viewModelScope.launch {
            accountRepository.postKakaoLogin(idToken)
                .onSuccess { loginResult ->
                    _uiState.setValue(LoginUiState.Succeed.from(loginResult.hasSurveyed))
                }.onFailure {
                    _uiState.setValue(LoginUiState.Failed)
                }
        }
    }
}
