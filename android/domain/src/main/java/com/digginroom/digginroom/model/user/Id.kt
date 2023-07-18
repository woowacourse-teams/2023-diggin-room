package com.digginroom.digginroom.model.user

@JvmInline
value class Id(val value: String) {

    init {
        require(value.length in MINIMUM_ID_LENGTH..MAXIMUM_ID_LENGTH) {
            CONDITION_ID_LENGTH
        }
    }

    companion object {

        private const val CONDITION_ID_LENGTH = "아이디의 길이는 5자 이상 11자 이하입니다."

        private const val MINIMUM_ID_LENGTH = 5
        private const val MAXIMUM_ID_LENGTH = 11
    }
}
