package com.digginroom.digginroom.model.user

@JvmInline
value class Password(val value: String) {

    init {
        require(value.length in MINIMUM_PASSWORD_LENGTH..MAXIMUM_PASSWORD_LENGTH) {
            PASSWORD_RULE
        }
        require(Regex(ESSENTIAL_PASSWORD_REGEX).containsMatchIn(value)) {
            PASSWORD_RULE
        }
    }

    companion object {

        private const val MINIMUM_PASSWORD_LENGTH = 8
        private const val MAXIMUM_PASSWORD_LENGTH = 16

        private const val PASSWORD_RULE = "비밀번호는 8자 이상 16자 이하이고 영문, 특수문자, 숫자를 포함해야합니다."

        private const val ESSENTIAL_PASSWORD_REGEX =
            "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,16}\$"
    }
}
