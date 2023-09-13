package com.digginroom.digginroom.model

data class JoinVerificationModel(
    val isValidId: Boolean = false,
    val isCheckedIdDuplication: Boolean = false,
    val isDuplicatedId: Boolean = false,
    val isVerifiedId: Boolean = false,
    val isValidPassword: Boolean = false,
    val isEqualReInputPassword: Boolean = false,
    val isJoinAble: Boolean = false
)
