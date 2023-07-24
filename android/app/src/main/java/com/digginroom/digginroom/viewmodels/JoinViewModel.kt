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

    fun validateId(id: String) {
        _isRedundancyChecked.value = false
        runCatching {
            Id(id)
        }.onSuccess {
            _isValidId.value = true
        }.onFailure {
            _isValidId.value = false
        }
        validateJoinAble()
    }

    fun validateIdRedundancy(id: String) {
        viewModelScope.launch {
            accountRepository.fetchIsDuplicatedId(Id(id))
                .onSuccess {
                    _isUniqueId.value = !it
                }.onFailure {
                }
            _isRedundancyChecked.value = true
            validateJoinAble()
        }
    }

    fun validatePassword(password: String) {
        runCatching {
            Password(password)
        }.onSuccess {
            _isValidPassword.value = true
        }.onFailure {
            _isValidPassword.value = false
        }
        validateJoinAble()
    }

    fun validatePasswordEquality(password: String, reInputPassword: String) {
        _isEqualPassword.value = password == reInputPassword
        validateJoinAble()
    }

    private fun validateJoinAble() {
        _isJoinAble.value = _isValidId.value == true &&
            _isValidPassword.value == true &&
            _isEqualPassword.value == true &&
            _isUniqueId.value == true
    }

    fun join(id: String, password: String) {
        _state.value = JoinState.Loading

        viewModelScope.launch {
            accountRepository.postJoin(
                Account(
                    id = Id(id),
                    password = Password(password)
                )
            ).onSuccess {
                _state.value = JoinState.Succeed
            }.onFailure {
                _state.value = JoinState.Failed()
            }
        }
    }
}
