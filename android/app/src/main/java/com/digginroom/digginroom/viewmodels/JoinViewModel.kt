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

    private val _isValidId = MutableLiveData(false)
    val isValidId: LiveData<Boolean>
        get() = _isValidId

    private val _isValidPassword = MutableLiveData(false)
    val isValidPassword: LiveData<Boolean>
        get() = _isValidPassword

    private val _isEqualPassword: MutableLiveData<Boolean> = MutableLiveData()
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
    }

    fun validatePassword(id: String) {
        runCatching {
            Password(id)
        }.onSuccess {
            _isValidPassword.value = true
        }.onFailure {
            _isValidPassword.value = false
        }
    }

    fun validatePasswordEquality(password: String, reInputPassword: String) {
        _isEqualPassword.value =
            password == reInputPassword
    }

    fun validateJoinAble() {
        _isJoinAble.value = _isValidId.value == true &&
            _isValidPassword.value == true &&
            _isEqualPassword.value == true
    }

    fun join(id: String, password: String) {
        userRepository.save(
            User(
                id = id,
                password = password
            )
        ).onSuccess {
            joinSucceed()
        }.onFailure {
            joinFailed()
        }
    }

    companion object {

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
