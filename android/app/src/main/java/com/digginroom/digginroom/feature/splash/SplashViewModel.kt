package com.digginroom.digginroom.feature.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.feature.login.LoginState
import com.digginroom.digginroom.repository.MemberRepository
import com.digginroom.digginroom.repository.TokenRepository
import kotlinx.coroutines.launch

class SplashViewModel(
    private val tokenRepository: TokenRepository,
    private val memberRepository: MemberRepository
) : ViewModel() {

    private val _loginState: MutableLiveData<LoginState> = MutableLiveData(LoginState.Start)
    val loginState: LiveData<LoginState>
        get() = _loginState

    fun validateToken() {
        viewModelScope.launch {
            tokenRepository.fetch().onSuccess {
                fetchMember(it)
            }.onFailure {
                _loginState.value = LoginState.Failed
            }
        }
    }

    private fun fetchMember(token: String) {
        viewModelScope.launch {
            memberRepository.fetch(token).onSuccess { member ->
                _loginState.value = LoginState.Succeed.from(member.hasSurveyed)
            }.onFailure {
                _loginState.value = LoginState.Failed
            }
        }
    }
}
