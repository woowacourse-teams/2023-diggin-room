package com.digginroom.digginroom.model.user

data class PasswordVerification(
    val isValid: Boolean = false,
    val isEqualReInput: Boolean = false
) {

    val isVerified = isValid && isEqualReInput

    fun checkIsValid(password: String): PasswordVerification {
        var isValid = false

        runCatching {
            Password(password)
        }.onSuccess {
            isValid = true
        }
        return PasswordVerification(isValid)
    }

    fun checkIsEqualReInput(password: String, reInputPassword: String) =
        PasswordVerification(
            isValid = isValid,
            isEqualReInput = password == reInputPassword
        )
}
