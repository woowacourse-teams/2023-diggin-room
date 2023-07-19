package com.digginroom.digginroom.model.user

@JvmInline
value class Id(val value: String) {

    init {
        require(value.length in MINIMUM_ID_LENGTH..MAXIMUM_ID_LENGTH) {
            ID_RULE
        }
        require(Regex(ESSENTIAL_ID_REGEX).containsMatchIn(value)) {
            ID_RULE
        }
    }

    companion object {

        private const val MINIMUM_ID_LENGTH = 5
        private const val MAXIMUM_ID_LENGTH = 11

        private const val ID_RULE = "아이디는 5자 이상 11자 이하의 영문, 숫자로 이루어져야합니다."

        private const val ESSENTIAL_ID_REGEX = "^[a-zA-Z0-9]{5,20}\$"
    }
}
