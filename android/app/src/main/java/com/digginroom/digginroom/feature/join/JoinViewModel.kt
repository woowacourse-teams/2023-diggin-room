package com.digginroom.digginroom.feature.join

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.model.JoinAccountModel
import com.digginroom.digginroom.model.mapper.JoinVerificationMapper.toModel
import com.digginroom.digginroom.model.user.Account
import com.digginroom.digginroom.model.user.Id
import com.digginroom.digginroom.model.user.IdVerification
import com.digginroom.digginroom.model.user.JoinVerification
import com.digginroom.digginroom.model.user.Password
import com.digginroom.digginroom.model.user.PasswordVerification
import com.digginroom.digginroom.repository.AccountRepository
import com.dygames.di.annotation.NotCaching
import kotlinx.coroutines.launch

@NotCaching
class JoinViewModel @Keep constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private var joinVerification = JoinVerification(
        idVerification = IdVerification(),
        passwordVerification = PasswordVerification()
    )

    private val _uiState: MutableLiveData<JoinUiState> =
        MutableLiveData(JoinUiState.InProgress(joinVerification.toModel()))
    val uiState: LiveData<JoinUiState>
        get() = _uiState

    fun validateId(id: String) {
        with(joinVerification) {
            joinVerification =
                joinVerification.copy(idVerification = idVerification.checkIsValid(id))

            _uiState.value = JoinUiState.InProgress(joinVerification.toModel())
        }
    }

    fun validateIdDuplication(id: String) {
        viewModelScope.launch {
            accountRepository.fetchIsDuplicatedId(Id(id)).onSuccess {
                joinVerification = joinVerification.copy(
                    idVerification = joinVerification.idVerification.copy(
                        isCheckedDuplication = true,
                        isDuplicated = it
                    )
                )

                _uiState.value = JoinUiState.InProgress(joinVerification.toModel())
            }.onFailure {}
        }
    }

    fun validatePassword(password: String, reInputPassword: String) {
        runCatching {
            Password(password)
        }.onSuccess {
            joinVerification = joinVerification.copy(
                passwordVerification = joinVerification.passwordVerification.copy(isValid = true)
            )
            _uiState.value = JoinUiState.InProgress(joinVerification.toModel())
        }.onFailure {
            joinVerification = joinVerification.copy(
                passwordVerification = joinVerification.passwordVerification.copy(isValid = false)
            )
            _uiState.value = JoinUiState.InProgress(joinVerification.toModel())
        }
        validatePasswordEquality(password, reInputPassword)
    }

    fun validatePasswordEquality(password: String, reInputPassword: String) {
        joinVerification = joinVerification.copy(
            passwordVerification = joinVerification.passwordVerification.checkIsEqualReInput(
                password = password,
                reInputPassword = reInputPassword
            )
        )

        _uiState.value = JoinUiState.InProgress(joinVerification.toModel())
    }

    fun join(account: JoinAccountModel) {
        viewModelScope.launch {
            _uiState.value = JoinUiState.Loading
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
