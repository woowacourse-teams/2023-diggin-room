package com.digginroom.digginroom.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.model.user.Account
import com.digginroom.digginroom.model.user.Id
import com.digginroom.digginroom.model.user.Password
import com.digginroom.digginroom.repository.AccountRepository
import com.digginroom.digginroom.views.activity.JoinState
import kotlinx.coroutines.launch

class JoinViewModel(private val accountRepository: AccountRepository) : ViewModel() {

    val id: NonNullMutableLiveData<String> = NonNullMutableLiveData(EMPTY_STRING)
    val password: NonNullMutableLiveData<String> = NonNullMutableLiveData(EMPTY_STRING)
    val reInputPassword: NonNullMutableLiveData<String> = NonNullMutableLiveData(EMPTY_STRING)

    private val _isValidId = MutableLiveData(false)
    val isValidId: LiveData<Boolean>
        get() = _isValidId

    private val _isRedundancyChecked: MutableLiveData<Boolean> = MutableLiveData(false)
    val isRedundancyChecked: LiveData<Boolean>
        get() = _isRedundancyChecked

    private val _isUniqueId: MutableLiveData<Boolean> = MutableLiveData()
    val isUniqueId: LiveData<Boolean>
        get() = _isUniqueId

    private val _isValidPassword = MutableLiveData(false)
    val isValidPassword: LiveData<Boolean>
        get() = _isValidPassword

    private val _isEqualPassword: MutableLiveData<Boolean> = MutableLiveData(true)
    val isEqualPassword: LiveData<Boolean>
        get() = _isEqualPassword

    private val _isJoinAble: MutableLiveData<Boolean> = MutableLiveData(false)
    val isJoinAble: LiveData<Boolean>
        get() = _isJoinAble

    private val _state: MutableLiveData<JoinState> = MutableLiveData(JoinState.Start)
    val state: LiveData<JoinState>
        get() = _state

    fun validateId() {
        _isRedundancyChecked.value = false
        runCatching {
            Id(id.value)
        }.onSuccess {
            _isValidId.value = true
        }.onFailure {
            _isValidId.value = false
        }
        validateJoinAble()
    }

    fun validateIdRedundancy() {
        viewModelScope.launch {
            accountRepository.fetchIsDuplicatedId(Id(id.value))
                .onSuccess {
                    _isUniqueId.value = !it
                }.onFailure {
                }
            _isRedundancyChecked.value = true
            validateJoinAble()
        }
    }

    fun validatePassword() {
        runCatching {
            Password(password.value)
        }.onSuccess {
            _isValidPassword.value = true
        }.onFailure {
            _isValidPassword.value = false
        }
        validatePasswordEquality()
    }

    fun validatePasswordEquality() {
        _isEqualPassword.value = password.value == reInputPassword.value
        validateJoinAble()
    }

    private fun validateJoinAble() {
        _isJoinAble.value = _isValidId.value == true &&
                _isRedundancyChecked.value == true &&
                _isValidPassword.value == true &&
                _isEqualPassword.value == true &&
                _isUniqueId.value == true
    }

    fun join() {
        _state.value = JoinState.Loading

        viewModelScope.launch {
            accountRepository.postJoin(
                Account(
                    id = Id(id.value),
                    password = Password(password.value)
                )
            ).onSuccess {
                _state.value = JoinState.Succeed
            }.onFailure {
                _state.value = JoinState.Failed()
            }
        }
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}
