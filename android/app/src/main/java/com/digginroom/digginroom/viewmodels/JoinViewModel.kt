package com.digginroom.digginroom.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.digginroom.digginroom.data.DefaultUserRepository
import com.digginroom.model.user.Id
import com.digginroom.model.user.JoinState
import com.digginroom.model.user.Password
import com.digginroom.model.user.User
import com.digginroom.repository.UserRepository

class JoinViewModel(
    private val userRepository: UserRepository,
    private val joinSucceed: () -> Unit,
    private val joinFailed: () -> Unit
) : ViewModel() {

    private val _isValidIdForm = MutableLiveData(false)
    val isValidIdForm: LiveData<Boolean>
        get() = _isValidIdForm

    private val _isValidPasswordForm = MutableLiveData(false)
    val isValidPasswordForm: LiveData<Boolean>
        get() = _isValidIdForm

    fun validateIdForm(value: String) {
        runCatching {
            Id(value)
        }.onFailure {
            _isValidIdForm.value = false
        }.onSuccess {
            _isValidIdForm.value = true
        }
    }

    fun validatePasswordForm(value: String) {
        runCatching {
            Password(value)
        }.onFailure {
            _isValidPasswordForm.value = false
        }.onSuccess {
            _isValidPasswordForm.value = true
        }
    }

    fun join(id: String, password: String) {
        val joinState = userRepository.save(
            User(
                id = id,
                password = password
            )
        )
        when (joinState) {
            is JoinState.Success -> joinSucceed()
            is JoinState.Retry -> joinFailed()
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
