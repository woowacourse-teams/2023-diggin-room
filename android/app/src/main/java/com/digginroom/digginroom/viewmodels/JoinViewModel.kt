package com.digginroom.digginroom.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.digginroom.digginroom.data.DefaultAccountRepository
import com.digginroom.model.user.Account
import com.digginroom.model.user.Id
import com.digginroom.model.user.Password
import com.digginroom.repository.AccountRepository
import kotlinx.coroutines.launch

class JoinViewModel(
    private val accountRepository: AccountRepository,
    private val joinSucceed: () -> Unit,
    private val joinFailed: () -> Unit
) : ViewModel() {

    // todo: editText는 회전이 일어나도 데이터가 유지된다. 이 상황에서 이 정보들을(id, password) 가지고 있어야 하는 이유는?
    // 1. onClick을 xml상에서 처리하기 위해? -> view딴에서만 현재 입력된 id와 password를 가지는 경우 xml상에서 click event를 어떻게 설정해야 할 지 모르겠다.
    // 2. ??
    private val _id: MutableLiveData<String> = MutableLiveData()
    private val _password: MutableLiveData<String> = MutableLiveData()

    private val _isValidId = MutableLiveData(false)
    val isValidId: LiveData<Boolean>
        get() = _isValidId

    private val _isValidPassword = MutableLiveData(false)
    val isValidPassword: LiveData<Boolean>
        get() = _isValidPassword

    private val _isEqualPassword: MutableLiveData<Boolean> = MutableLiveData(true)
    val isEqualPassword: LiveData<Boolean>
        get() = _isEqualPassword

    private val _isJoinAble: MutableLiveData<Boolean> = MutableLiveData(false)
    val isJoinAble: LiveData<Boolean>
        get() = _isJoinAble

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    // todo: exception message를 error message로 쓰이는 형태로 개선 예정
    fun validateId(id: String) {
        runCatching {
            Id(id)
        }.onSuccess {
            _isValidId.value = true
        }.onFailure {
            _isValidId.value = false
        }
        _id.value = id
        validateJoinAble()
    }

    fun validatePassword(password: String) {
        runCatching {
            Password(password)
        }.onSuccess {
            _isValidPassword.value = true
        }.onFailure {
            _isValidPassword.value = false
        }
        _password.value = password
        validateJoinAble()
    }

    fun validatePasswordEquality(reInputPassword: String) {
        _isEqualPassword.value = _password.value == reInputPassword
    }

    private fun validateJoinAble() {
        _isJoinAble.value = _isValidId.value == true &&
            _isValidPassword.value == true &&
            _isEqualPassword.value == true
    }

    fun join() {
        _isLoading.value = true

        viewModelScope.launch {
            accountRepository.saveAccount(
                Account(
                    id = Id(_id.value ?: EMPTY),
                    password = Password(_password.value ?: EMPTY)
                )
            ).onSuccess {
                joinSucceed()
            }.onFailure {
                joinFailed()
            }
            _isLoading.value = false
        }
    }

    companion object {

        private const val EMPTY = ""

        fun getJoinViewModelFactory(
            joinSucceed: () -> Unit,
            joinFailed: () -> Unit
        ): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    JoinViewModel(
                        accountRepository = DefaultAccountRepository(),
                        joinSucceed = joinSucceed,
                        joinFailed = joinFailed
                    )
                }
            }
        }
    }
}
