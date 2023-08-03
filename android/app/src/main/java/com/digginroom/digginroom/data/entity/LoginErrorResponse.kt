package com.digginroom.digginroom.data.entity

sealed class LoginErrorResponse(override val message: String?) : Throwable(message) {

    data class EmptyToken(override val message: String? = ERROR_MESSAGE_NO_TOKEN) :
        LoginErrorResponse(message)

    data class NoSuchAccount(override val message: String? = ERROR_MESSAGE_ACCOUNT) :
        LoginErrorResponse(message)

    companion object {

        private const val ERROR_MESSAGE_NO_TOKEN = "쿠기 정보를 받아오지 못했습니다."
        private const val ERROR_MESSAGE_ACCOUNT = "회원 정보가 존재하지 않습니다."
    }
}