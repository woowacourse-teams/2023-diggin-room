package com.digginroom.digginroom.data.entity

class CancelScrapErrorResponse(override val message: String) : Throwable() {

    data class NotExistRoom(
        override val message: String = ERROR_MESSAGE_NOT_EXIST_ROOM
    ) : ScrapErrorResponse(message)

    data class Unauthorized(
        override val message: String = ERROR_MESSAGE_UNAUTHORIZED
    ) : ScrapErrorResponse(message)

    data class Unknown(
        override val message: String = ERROR_MESSAGE_UNKNOWN
    ) : ScrapErrorResponse(message)

    companion object {

        private const val ERROR_MESSAGE_NOT_EXIST_ROOM = "해당 룸이 없습니다."
        private const val ERROR_MESSAGE_UNAUTHORIZED = "회원 인증 정보가 존재하지 않습니다."
        private const val ERROR_MESSAGE_UNKNOWN = "알 수 없는 에러"

        fun from(code: Int): ScrapErrorResponse =
            when (code) {
                400 -> NotExistRoom()
                401 -> Unauthorized()
                else -> Unknown()
            }
    }
}
