package com.digginroom.digginroom.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

class LoginViewModel(private val accountRepository: AccountRepository) : ViewModel() {

    private val _isLoginSucceed: MutableLiveData<Boolean> = MutableLiveData()
    val isLoginSucceed: LiveData<Boolean>
        get() = _isLoginSucceed

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun login(id: String, password: String) {
        runCatching {
            viewModelScope.launch {
                accountRepository.postAccount(
                    Account(
                        id = Id(id),
                        password = Password(password),
                    ),
                )
            }
        }.onSuccess {
            _isLoginSucceed.value = true
        }.onFailure {
            _isLoginSucceed.value = false
        }
    }

    companion object {

        fun getLoginViewModelFactory(): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    LoginViewModel(DefaultAccountRepository())
                }
            }
    }
}
