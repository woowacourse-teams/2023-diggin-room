package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.data.entity.IdDuplicationResponse
import com.digginroom.digginroom.data.entity.JoinErrorResponse
import com.digginroom.digginroom.data.entity.JoinRequest
import com.digginroom.digginroom.data.entity.LoginRequest
import com.digginroom.digginroom.data.service.AccountService
import retrofit2.Response

class AccountRemoteDataSource(
    private val accountService: AccountService = NetworkModule.accountService
) {

    suspend fun postJoin(id: String, password: String) {
        val response: Response<Void> = accountService.postJoin(
            JoinRequest(
                id = id,
                password = password
            )
        )

        if (!response.isSuccessful) {
            throw JoinErrorResponse.from(response.code())
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
            return response.headers().get(SET_COOKIE)
                ?: throw JoinErrorResponse.from(response.code())
        }
        throw JoinErrorResponse.from(response.code())
    }

    suspend fun fetchIsDuplicatedId(id: String): IdDuplicationResponse {
        val response: Response<IdDuplicationResponse> = accountService.fetchIsDuplicatedId(id)

        if (response.isSuccessful) {
            return response.body() ?: throw JoinErrorResponse.from(response.code())
        }
        throw JoinErrorResponse.from(response.code())
    }

    companion object {

        private const val SET_COOKIE = "Set-Cookie"
    }
}
