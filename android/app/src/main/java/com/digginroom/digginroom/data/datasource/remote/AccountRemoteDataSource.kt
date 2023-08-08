package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.data.entity.ErrorResponse
import com.digginroom.digginroom.data.entity.IdDuplicationResponse
import com.digginroom.digginroom.data.entity.JoinRequest
import com.digginroom.digginroom.data.entity.LoginRequest
import com.digginroom.digginroom.data.service.AccountService
import retrofit2.Response

class AccountRemoteDataSource(
    private val accountService: AccountService
) {

    suspend fun postJoin(id: String, password: String) {
        val response: Response<Void> = accountService.postJoin(
            JoinRequest(
                id = id,
                password = password
            )
        )

        if (response.code() == 400) throw ErrorResponse.BadRequest(response)

        if (response.code() != 201) throw ErrorResponse.Unknown(response)
    }

    suspend fun postLogin(id: String, password: String): String {
        val response = accountService.postLogin(
            LoginRequest(
                id = id,
                password = password
            )
        )

        if (response.code() == 400) throw ErrorResponse.BadRequest(response)

        if (response.code() == 200) {
            return response.headers().get(SET_COOKIE) ?: throw ErrorResponse.EmptyBody(response)
        }

        throw ErrorResponse.Unknown(response)
    }

    suspend fun fetchIsDuplicatedId(id: String): IdDuplicationResponse {
        val response: Response<IdDuplicationResponse> = accountService.fetchIsDuplicatedId(id)

        if (response.code() == 200) {
            return response.body() ?: throw ErrorResponse.EmptyBody(response)
        }

        throw ErrorResponse.Unknown(response)
    }

    companion object {

        private const val SET_COOKIE = "Set-Cookie"
    }
}
