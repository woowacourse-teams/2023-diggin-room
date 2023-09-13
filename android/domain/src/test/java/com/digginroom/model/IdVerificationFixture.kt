package com.digginroom.model

import com.digginroom.digginroom.model.user.IdVerification

object IdVerificationFixture {

    fun inProgress(): IdVerification = IdVerification(
        isValid = false,
        isCheckedDuplication = false,
        isDuplicated = false
    )

    fun complete(): IdVerification = IdVerification(
        isValid = true,
        isCheckedDuplication = true,
        isDuplicated = false
    )
}
