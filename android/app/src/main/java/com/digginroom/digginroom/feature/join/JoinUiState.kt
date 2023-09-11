package com.digginroom.digginroom.feature.join

import com.digginroom.digginroom.model.JoinAccountModel

sealed class JoinUiState(
    open val isValidId: Boolean = false,
    open val isCheckedIdDuplication: Boolean = false,
    open val isDuplicatedId: Boolean = false,
    open val isValidPassword: Boolean = false,
    open val isEqualReInputPassword: Boolean = false
) {
    val isIdVerified: Boolean
        get() = isValidId &&
            isCheckedIdDuplication &&
            !isDuplicatedId

    private val isPasswordVerified: Boolean
        get() = isValidPassword && isEqualReInputPassword

    val isJoinAble: Boolean
        get() = isIdVerified && isPasswordVerified

    fun setInProgress(
        isValidId: Boolean = this.isValidId,
        isCheckedIdDuplication: Boolean = this.isCheckedIdDuplication,
        isDuplicatedId: Boolean = this.isDuplicatedId,
        isValidPassword: Boolean = this.isValidPassword,
        isEqualReInputPassword: Boolean = this.isEqualReInputPassword
    ): JoinUiState = InProgress(
        isValidId = isValidId,
        isCheckedIdDuplication = isCheckedIdDuplication,
        isDuplicatedId = isDuplicatedId,
        isValidPassword = isValidPassword,
        isEqualReInputPassword = isEqualReInputPassword
    )

    data class InProgress(
        override val isValidId: Boolean = false,
        override val isCheckedIdDuplication: Boolean = false,
        override val isDuplicatedId: Boolean = false,
        override val isValidPassword: Boolean = false,
        override val isEqualReInputPassword: Boolean = false
    ) : JoinUiState(
        isValidId = isValidId,
        isCheckedIdDuplication = isCheckedIdDuplication,
        isDuplicatedId = isDuplicatedId,
        isValidPassword = isValidPassword,
        isEqualReInputPassword = isEqualReInputPassword
    )

    object Loading : JoinUiState()

    object Succeed : JoinUiState()

    data class Failed(
        val account: JoinAccountModel = JoinAccountModel()
    ) : JoinUiState()
}
