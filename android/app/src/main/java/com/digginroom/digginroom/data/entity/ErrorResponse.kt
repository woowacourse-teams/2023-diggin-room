package com.digginroom.digginroom.data.entity

import retrofit2.Response

sealed class ErrorResponse(
    open val code: Int,
    override val message: String
) : Throwable(message) {
    data class BadRequest(
        val response: Response<*>
    ) : ErrorResponse(response.code(), response.message())

    data class Unauthorized(
        val response: Response<*>
    ) : ErrorResponse(response.code(), response.message())

    data class EmptyBody(
        val response: Response<*>
    ) : ErrorResponse(response.code(), "")

    data class Unknown(
        val response: Response<*>
    ) : ErrorResponse(response.code(), "")
}
