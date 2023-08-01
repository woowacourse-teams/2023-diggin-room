package com.digginroom.digginroom.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.livedata.NonNullMutableLiveData
import com.digginroom.digginroom.repository.AccountRepository
import com.digginroom.digginroom.repository.TokenRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val accountRepository: AccountRepository,
    private val tokenRepository: TokenRepository,
) : ViewModel() {

    val id: NonNullMutableLiveData<String> = NonNullMutableLiveData(EMPTY)
    val password: NonNullMutableLiveData<String> = NonNullMutableLiveData(EMPTY)

    private val _state: MutableLiveData<LoginState> = MutableLiveData(LoginState.START)
    val state: LiveData<LoginState>
        get() = _state

    fun login() {
        _state.value = LoginState.LOADING

        viewModelScope.launch {
            accountRepository.postLogIn(
                id = id.value,
                password = password.value,
            ).onSuccess {
                _state.value = LoginState.SUCCEED
                tokenRepository.save(it)
            }.onFailure {
                _state.value = LoginState.FAILED
            }
        }
    }

    companion object {

        private const val EMPTY = ""
    }
}
