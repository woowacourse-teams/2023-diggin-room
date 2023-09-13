package com.digginroom.model

import com.digginroom.digginroom.model.user.PasswordVerification

object PasswordVerificationFixture {

    fun inProgress(): PasswordVerification = PasswordVerification(
        isValid = false,
        isEqualReInput = false
    )

    fun complete(): PasswordVerification = PasswordVerification(
        isValid = true,
        isEqualReInput = true
    )
}
