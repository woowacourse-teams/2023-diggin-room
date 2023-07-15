package com.digginroom.digginroom.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.digginroom.digginroom.data.DefaultUserRepository
import com.digginroom.model.user.Id
import com.digginroom.model.user.Password
import com.digginroom.model.user.User
import com.digginroom.repository.UserRepository

class JoinViewModel(
    private val userRepository: UserRepository,
    private val joinSucceed: () -> Unit,
    private val joinFailed: () -> Unit
) : ViewModel() {

    // todo: editText는 회전이 일어나도 데이터가 유지된다. 이 상황에서 이 정보들을 가지고 있어야 하는 이유는?
    // 1. 바인딩 어댑터를 위해
    // 2. viewModel이라는 것이
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
        userRepository.save(
            User(
                id = Id(_id.value ?: EMPTY),
                password = Password(_password.value ?: EMPTY)
            )
        ).onSuccess {
            joinSucceed()
        }.onFailure {
            joinFailed()
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
                        userRepository = DefaultUserRepository(),
                        joinSucceed = joinSucceed,
                        joinFailed = joinFailed
                    )
                }
            }
        }
    }
}
