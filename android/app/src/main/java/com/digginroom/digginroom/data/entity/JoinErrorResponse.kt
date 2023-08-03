package com.digginroom.digginroom.data.entity

sealed class JoinErrorResponse(override val message: String) : Throwable() {

    data class DuplicatedId(
        override val message: String = ERROR_MESSAGE_DUPLICATED_ID
    ) : JoinErrorResponse(message)

    data class FailedCheckDuplicatedId(
        override val message: String = ERROR_MESSAGE_CANNOT_CHECK_DUPLICATED
    ) : JoinErrorResponse(message)

    data class Default(
        override val message: String = ERROR_MESSAGE_DEFAULT
    ) : JoinErrorResponse(message)

    companion object {

        private const val ERROR_MESSAGE_CANNOT_CHECK_DUPLICATED = "아이디가 중복되었는지 확인할 수 없습니다."
        private const val ERROR_MESSAGE_DUPLICATED_ID = "아이디가 중복되었습니다."
        private const val ERROR_MESSAGE_DEFAULT = "회원 가입을 실패했습니다."
    }
}
