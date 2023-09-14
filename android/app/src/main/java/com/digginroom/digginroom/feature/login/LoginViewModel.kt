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
        MutableSingleLiveData(LoginUiState.InProgress.Login)
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

    fun googleLogin(idToken: String) {
        _uiState.setValue(LoginUiState.Loading)

        viewModelScope.launch {
            accountRepository.postLogin(idToken)
                .onSuccess { loginResult ->
                    _uiState.setValue(LoginUiState.Succeed.from(loginResult.hasSurveyed))
                }.onFailure {
                    _uiState.setValue(LoginUiState.Failed)
                }
        }
    }

    fun startGoogleLogin() {
        _uiState.setValue(LoginUiState.InProgress.GoogleLogin)
    }

    fun startKakaoLogin() {
        _uiState.setValue(LoginUiState.InProgress.KaKaoLogin)
    }

    fun kakaoLogin(idToken: String) {
//        _uiState.value = LoginUiState.Loading
//
//        viewModelScope.launch {
//            accountRepository.postLogin(idToken)
//                .onSuccess { loginResult ->
//                    _uiState.value = LoginUiState.Succeed.from(loginResult.hasSurveyed)
//                }.onFailure {
//                    _uiState.value = LoginUiState.Failed
//                }
//        }
    }
}
