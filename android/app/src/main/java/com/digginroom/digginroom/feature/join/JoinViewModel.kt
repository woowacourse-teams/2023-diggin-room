package com.digginroom.digginroom.feature.join

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.model.JoinAccountModel
import com.digginroom.digginroom.model.user.Account
import com.digginroom.digginroom.model.user.Id
import com.digginroom.digginroom.model.user.Password
import com.digginroom.digginroom.repository.AccountRepository
import kotlinx.coroutines.launch

class JoinViewModel(private val accountRepository: AccountRepository) : ViewModel() {

    private val _uiState: MutableLiveData<JoinUiState> = MutableLiveData(JoinUiState.InProgress())
    val uiState: LiveData<JoinUiState>
        get() = _uiState

    fun validateId(id: String) {
        runCatching {
            Id(id)
        }.onSuccess {
            _uiState.value = _uiState
                .value
                ?.setInProgress(isValidId = true)
                ?.setInProgress(isCheckedIdDuplication = false)
        }.onFailure {
            _uiState.value = _uiState
                .value
                ?.setInProgress(isValidId = false)
                ?.setInProgress(isCheckedIdDuplication = false)
        }
    }

    fun validateIdDuplication(id: String) {
        viewModelScope.launch {
            accountRepository.fetchIsDuplicatedId(Id(id))
                .onSuccess {
                    _uiState.value = _uiState
                        .value
                        ?.setInProgress(isDuplicatedId = it)
                        ?.setInProgress(isCheckedIdDuplication = true)
                }.onFailure {
                }
        }
    }

    fun validatePassword(password: String, reInputPassword: String) {
        runCatching {
            Password(password)
        }.onSuccess {
            _uiState.value = _uiState.value?.setInProgress(isValidPassword = true)
        }.onFailure {
            _uiState.value = _uiState.value?.setInProgress(isValidPassword = false)
        }
        validatePasswordEquality(password, reInputPassword)
    }

    fun validatePasswordEquality(password: String, reInputPassword: String) {
        _uiState.value =
            _uiState.value?.setInProgress(isEqualReInputPassword = password == reInputPassword)
    }

    fun join(account: JoinAccountModel) {
        viewModelScope.launch {
            accountRepository.postJoin(
                Account(
                    id = Id(account.id),
                    password = Password(account.password)
                )
            ).onSuccess {
                _uiState.value = JoinUiState.Succeed
            }.onFailure {
                _uiState.value = JoinUiState.Failed()
            }
        }
    }
}
