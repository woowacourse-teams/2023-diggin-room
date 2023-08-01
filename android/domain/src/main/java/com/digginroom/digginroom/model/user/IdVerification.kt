package com.digginroom.digginroom.model.user

data class IdVerification(
    val isValid: Boolean = false,
    val isCheckedDuplication: Boolean = false,
    val isDuplicated: Boolean = false
) {
    val isVerified: Boolean = isValid && isCheckedDuplication && !isDuplicated

    fun checkIsValid(id: String): IdVerification {
        runCatching {
            Id(id)
        }.onSuccess {
            return IdVerification(
                isValid = true,
                isCheckedDuplication = isCheckedDuplication,
                isDuplicated = isDuplicated
            )
        }
        return IdVerification(
            isValid = false,
            isCheckedDuplication = isCheckedDuplication,
            isDuplicated = isDuplicated
        )
    }

    fun setIsCheckedDuplication(value: Boolean): IdVerification =
        IdVerification(
            isValid = isValid,
            isCheckedDuplication = value,
            isDuplicated = isDuplicated
        )

    fun setIsDuplicated(value: Boolean): IdVerification =
        IdVerification(
            isValid = isValid,
            isCheckedDuplication = isCheckedDuplication,
            isDuplicated = value
        )
}
