package com.digginroom.digginroom.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.livedata.NonNullMutableLiveData
import com.digginroom.digginroom.repository.AccountRepository
import com.digginroom.digginroom.util.SingleLiveEvent
import kotlinx.coroutines.launch

class LoginViewModel(
    private val accountRepository: AccountRepository
) : ViewModel() {

    val id: NonNullMutableLiveData<String> = NonNullMutableLiveData(EMPTY_STRING)
    val password: NonNullMutableLiveData<String> = NonNullMutableLiveData(EMPTY_STRING)

    private val _googleLoginEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val googleLoginEvent: LiveData<Any>
        get() = _googleLoginEvent

    private val _state: MutableLiveData<LoginState> = MutableLiveData()
    val state: LiveData<LoginState>
        get() = _state

    fun login() {
        _state.value = LoginState.Loading

        viewModelScope.launch {
            accountRepository.postLogIn(
                id = id.value,
                password = password.value
            ).onSuccess { loginResult ->
                _state.value = LoginState.Succeed.from(loginResult.hasSurveyed)
            }.onFailure {
                _state.value = LoginState.Failed
            }
        }
    }

    fun startGoogleLogin() {
        _googleLoginEvent.call()
    }

    fun login(idToken: String) {
        _state.value = LoginState.Loading

        viewModelScope.launch {
            accountRepository.postLogin(idToken)
                .onSuccess { loginResult ->
                    _state.value = LoginState.Succeed.from(loginResult.hasSurveyed)
                }.onFailure {
                    _state.value = LoginState.Failed
                }
        }
    }

    companion object {

        private const val EMPTY_STRING = ""
    }
}
