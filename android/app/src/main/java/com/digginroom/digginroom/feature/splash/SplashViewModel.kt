package com.digginroom.digginroom.feature.splash

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.feature.login.LoginUiState
import com.digginroom.digginroom.repository.MemberRepository
import com.digginroom.digginroom.repository.TokenRepository
import com.dygames.di.annotation.NotCaching
import kotlinx.coroutines.launch

@NotCaching
class SplashViewModel @Keep constructor(
    private val tokenRepository: TokenRepository,
    private val memberRepository: MemberRepository
) : ViewModel() {

    private val _loginUiState: MutableLiveData<LoginUiState> = MutableLiveData()
    val loginUiState: LiveData<LoginUiState>
        get() = _loginUiState

    fun validateToken() {
        viewModelScope.launch {
            tokenRepository.fetch().onSuccess {
                fetchMember(it)
            }.onFailure {
                _loginUiState.value = LoginUiState.Failed.AutoLogin
            }
        }
    }

    private fun fetchMember(token: String) {
        viewModelScope.launch {
            memberRepository.fetch(token).onSuccess { member ->
                _loginUiState.value = LoginUiState.Succeed.from(member.hasSurveyed)
            }.onFailure {
                _loginUiState.value = LoginUiState.Failed.AutoLogin
            }
        }
    }
}
