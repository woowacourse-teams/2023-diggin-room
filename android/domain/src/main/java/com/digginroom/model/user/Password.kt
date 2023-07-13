package com.digginroom.model.user

@JvmInline
value class Password(val value: String) {

    init {
        require(Regex(ESSENTIAL_PASSWORD_REGEX).containsMatchIn(value)) {
            PASSWORD_CONDITION
        }
    }

    companion object {

        private const val PASSWORD_CONDITION = "비밀번호는 영문, 숫자 조합의 8자 이상 15자 이하입니다."

        private const val ESSENTIAL_PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*\\d).{8,15}$"
    }
}
