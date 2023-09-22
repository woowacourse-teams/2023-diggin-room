package com.digginroom.digginroom.model.user

data class IdVerification(
    val isValid: Boolean = false,
    val isCheckedDuplication: Boolean = false,
    val isDuplicated: Boolean = false
) {
    val isVerified: Boolean = isValid && isCheckedDuplication && !isDuplicated

    fun checkIsValid(id: String): IdVerification {
        var isValid = false

        runCatching {
            Id(id)
        }.onSuccess {
            isValid = true
        }
        return IdVerification(
            isValid = isValid,
            isCheckedDuplication = false,
            isDuplicated = false
        )
    }
}
