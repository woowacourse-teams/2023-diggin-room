package com.digginroom.digginroom.model.user

data class PasswordVerification(
    val isValid: Boolean = false,
    val isEqualReInput: Boolean = false
) {

    val isVerified = isValid && isEqualReInput

    fun checkIsValid(password: String): PasswordVerification {
        runCatching {
            Password(password)
        }.onSuccess {
            return PasswordVerification(
                isValid = true,
                isEqualReInput = false
            )
        }
        return PasswordVerification(
            isValid = false,
            isEqualReInput = false
        )
    }

    fun setIsEqualReInput(value: Boolean) =
        PasswordVerification(
            isValid = isValid,
            isEqualReInput = value
        )
}
