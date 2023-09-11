package com.digginroom.digginroom.feature.join

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.livedata.NonNullMutableLiveData
import com.digginroom.digginroom.model.JoinAccountModel
import com.digginroom.digginroom.model.user.Account
import com.digginroom.digginroom.model.user.Id
import com.digginroom.digginroom.model.user.IdVerification
import com.digginroom.digginroom.model.user.Password
import com.digginroom.digginroom.model.user.PasswordVerification
import com.digginroom.digginroom.repository.AccountRepository
import kotlinx.coroutines.launch

class JoinViewModel(private val accountRepository: AccountRepository) : ViewModel() {

    private val _idVerification: NonNullMutableLiveData<IdVerification> =
        NonNullMutableLiveData(IdVerification())
    val idVerification: LiveData<IdVerification>
        get() = _idVerification

    private val _passwordVerification: NonNullMutableLiveData<PasswordVerification> =
        NonNullMutableLiveData(PasswordVerification())
    val passwordVerification: LiveData<PasswordVerification>
        get() = _passwordVerification

    private val _isJoinAble: MutableLiveData<Boolean> = MutableLiveData(false)
    val isJoinAble: LiveData<Boolean>
        get() = _isJoinAble

    private val _state: MutableLiveData<JoinState> = MutableLiveData(JoinState.Start)
    val state: LiveData<JoinState>
        get() = _state

    fun validateId(id: String) {
        with(_idVerification) {
            value = value.setIsCheckedDuplication(false)
                .checkIsValid(id)
        }
        validateJoinAble()
    }

    fun validateIdDuplication(id: String) {
        viewModelScope.launch {
            accountRepository.fetchIsDuplicatedId(Id(id))
                .onSuccess {
                    _idVerification.value = _idVerification.value.setIsDuplicated(it)
                }.onFailure {
                }
            _idVerification.value = _idVerification.value.setIsCheckedDuplication(true)
            validateJoinAble()
        }
    }

    fun validatePassword(password: String, reInputPassword: String) {
        with(_passwordVerification) {
            value = value.checkIsValid(password)
        }
        validatePasswordEquality(password, reInputPassword)
    }

    fun validatePasswordEquality(password: String, reInputPassword: String) {
        with(_passwordVerification) {
            value = value.setIsEqualReInput(password == reInputPassword)
        }
        validateJoinAble()
    }

    private fun validateJoinAble() {
        _isJoinAble.value =
            _idVerification.value.isVerified && _passwordVerification.value.isVerified
    }

    fun join(account: JoinAccountModel) {
        _state.value = JoinState.Loading

        viewModelScope.launch {
            accountRepository.postJoin(
                Account(
                    id = Id(account.id),
                    password = Password(account.password)
                )
            ).onSuccess {
                _state.value = JoinState.Succeed
            }.onFailure {
                _state.value = JoinState.Failed()
            }
        }
    }
}
