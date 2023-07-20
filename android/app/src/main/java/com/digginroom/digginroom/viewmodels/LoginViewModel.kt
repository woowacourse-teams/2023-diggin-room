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
import com.digginroom.digginroom.model.user.Account
import com.digginroom.digginroom.model.user.Id
import com.digginroom.digginroom.model.user.Password
import com.digginroom.digginroom.repository.AccountRepository
import com.digginroom.digginroom.repository.TokenRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val accountRepository: AccountRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _isLoginSucceed: MutableLiveData<Boolean> = MutableLiveData()
    val isLoginSucceed: LiveData<Boolean>
        get() = _isLoginSucceed

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun login(id: String, password: String) {
        viewModelScope.launch {
            accountRepository.postLogIn(
                Account(
                    id = Id(id),
                    password = Password(password)
                )
            ).onSuccess {
                _isLoginSucceed.value = true
                tokenRepository.save(it)
            }.onFailure {
                _isLoginSucceed.value = false
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
