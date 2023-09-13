package com.digginroom.digginroom.model.mapper

import com.digginroom.digginroom.model.JoinVerificationModel
import com.digginroom.digginroom.model.user.IdVerification
import com.digginroom.digginroom.model.user.JoinVerification
import com.digginroom.digginroom.model.user.PasswordVerification

object JoinVerificationMapper {

    fun JoinVerification.toModel(): JoinVerificationModel =
        JoinVerificationModel(
            isValidId = this.idVerification.isValid,
            isCheckedIdDuplication = this.idVerification.isCheckedDuplication,
            isDuplicatedId = this.idVerification.isDuplicated,
            isVerifiedId = this.idVerification.isVerified,
            isValidPassword = this.passwordVerification.isValid,
            isEqualReInputPassword = this.passwordVerification.isEqualReInput,
            isJoinAble = this.isJoinAble
        )

    fun JoinVerificationModel.toDomain(): JoinVerification =
        JoinVerification(
            idVerification = IdVerification(
                isValid = isValidId,
                isCheckedDuplication = isCheckedIdDuplication,
                isDuplicated = isDuplicatedId
            ),
            passwordVerification = PasswordVerification(
                isValid = false,
                isEqualReInput = false
            )
        )
}
