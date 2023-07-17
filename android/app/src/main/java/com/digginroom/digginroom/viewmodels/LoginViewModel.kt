package com.digginroom.digginroom.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.digginroom.digginroom.data.repository.DefaultAccountRepository
import com.digginroom.model.user.Account
import com.digginroom.model.user.Id
import com.digginroom.model.user.Password
import com.digginroom.repository.AccountRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val accountRepository: AccountRepository,
    private val loginSucceed: () -> Unit,
    private val loginFailed: () -> Unit
) : ViewModel() {

    fun login(id: String, password: String) {
        runCatching {
            viewModelScope.launch {
                accountRepository.postAccount(
                    Account(
                        id = Id(id),
                        password = Password(password)
                    )
                )
            }
        }.onSuccess {
            loginSucceed()
        }.onFailure {
            loginFailed()
        }
    }

    companion object {

        fun getLoginViewModelFactory(
            loginSucceed: () -> Unit,
            loginFailed: () -> Unit
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LoginViewModel(
                    accountRepository = DefaultAccountRepository(),
                    loginSucceed = loginSucceed,
                    loginFailed = loginFailed
                )
            }
        }
    }
}
