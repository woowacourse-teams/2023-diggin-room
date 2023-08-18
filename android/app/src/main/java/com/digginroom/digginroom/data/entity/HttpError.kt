package com.digginroom.digginroom.data.entity

import retrofit2.Response

sealed class HttpError(
    val code: Int,
    override val message: String
) : Throwable(message) {
    class BadRequest(
        response: Response<*>
    ) : HttpError(response.code(), response.message())

    class Unauthorized(
        response: Response<*>
    ) : HttpError(response.code(), response.message())

    class EmptyBody(
        response: Response<*>
    ) : HttpError(response.code(), "")

    class Unknown(
        response: Response<*>
    ) : HttpError(response.code(), "${response.code()} : ${response.body()}")
}
