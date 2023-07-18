package com.digginroom.digginroom.data.entity

sealed class AccountError(override val message: String) : Throwable() {

    data class Unknown(override val message: String = ERROR_MESSAGE_UNKNOWN) : AccountError(message)

    companion object {

        private const val ERROR_MESSAGE_UNKNOWN = "알 수 없는 에러"
    }
}
