package com.digginroom.digginroom.model.user

data class JoinVerification(
    val idVerification: IdVerification,
    val passwordVerification: PasswordVerification
) {

    val isJoinAble: Boolean
        get() = idVerification.isVerified && passwordVerification.isVerified

    fun setIdVerification(idVerification: IdVerification) {
    }
}
