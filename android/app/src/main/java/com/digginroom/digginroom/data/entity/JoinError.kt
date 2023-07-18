package com.digginroom.digginroom.data.entity

sealed class JoinError(override val message: String) : Throwable() {

    data class DuplicatedId(
        override val message: String = ERROR_MESSAGE_DUPLICATED_ID
    ) : JoinError(message)

    data class Unknown(
        override val message: String = ERROR_MESSAGE_UNKNOWN
    ) : JoinError(message)

    companion object {

        private const val ERROR_MESSAGE_DUPLICATED_ID = "아이디가 중복되었습니다."
        private const val ERROR_MESSAGE_UNKNOWN = "알 수 없는 에러"

        fun from(code: Int): JoinError =
            when (code) {
                400 -> DuplicatedId()
                else -> Unknown()
            }
    }
}
