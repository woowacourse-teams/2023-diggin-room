package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.data.entity.IdDuplicationResponse
import com.digginroom.digginroom.data.entity.JoinErrorResponse
import com.digginroom.digginroom.data.entity.JoinRequest
import com.digginroom.digginroom.data.entity.LoginErrorResponse
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

        if (!response.isSuccessful) {
            throw JoinErrorResponse.Default()
        }
    }

    suspend fun postLogin(id: String, password: String): String {
        val response = accountService.postLogin(
            LoginRequest(
                id = id,
                password = password
            )
        )

        if (response.isSuccessful) {
            return response.headers().get(SET_COOKIE) ?: throw LoginErrorResponse.EmptyToken()
        }
        throw LoginErrorResponse.NoSuchAccount()
    }

    suspend fun fetchIsDuplicatedId(id: String): IdDuplicationResponse {
        val response: Response<IdDuplicationResponse> = accountService.fetchIsDuplicatedId(id)

        if (response.isSuccessful) {
            return response.body() ?: throw JoinErrorResponse.FailedCheckDuplicatedId()
        }
        throw JoinErrorResponse.FailedCheckDuplicatedId()
    }

    companion object {

        private const val SET_COOKIE = "Set-Cookie"
    }
}
