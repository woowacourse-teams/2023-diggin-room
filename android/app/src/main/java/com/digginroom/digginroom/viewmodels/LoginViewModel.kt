package com.digginroom.digginroom.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.digginroom.digginroom.data.datasource.local.TokenLocalDataSource
import com.digginroom.digginroom.data.repository.DefaultAccountRepository
import com.digginroom.digginroom.data.repository.DefaultTokenRepository
import com.digginroom.digginroom.repository.AccountRepository
import com.digginroom.digginroom.repository.TokenRepository
import com.digginroom.digginroom.views.activity.LoginState
import kotlinx.coroutines.launch

class LoginViewModel(
    private val accountRepository: AccountRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _state: MutableLiveData<LoginState> = MutableLiveData(LoginState.START)
    val state: LiveData<LoginState>
        get() = _state

    fun login(id: String, password: String) {
        _state.value = LoginState.LOADING
        viewModelScope.launch {
            accountRepository.postLogIn(
                id = id,
                password = password
            ).onSuccess {
                _state.value = LoginState.SUCCEED
                tokenRepository.save(it)
            }.onFailure {
                _state.value = LoginState.FAILED
            }
        }
    }

    companion object {

        fun getLoginViewModelFactory(application: Application): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    LoginViewModel(
                        accountRepository = DefaultAccountRepository(),
                        tokenRepository = DefaultTokenRepository(
                            TokenLocalDataSource(application.applicationContext)
                        )
                    )
                }
            }
    }
}
