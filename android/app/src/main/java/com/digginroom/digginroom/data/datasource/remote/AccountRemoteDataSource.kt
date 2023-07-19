package com.digginroom.digginroom.data.datasource.remote

import android.util.Log
import com.digginroom.digginroom.data.entity.IdDuplicationResponse
import com.digginroom.digginroom.data.entity.JoinErrorResponse
import com.digginroom.digginroom.data.service.AccountService
import retrofit2.Response
import java.io.IOException

class AccountRemoteDataSource(
    private val accountService: AccountService = NetworkModule.accountService
) {

    suspend fun saveAccount(id: String, password: String) {
        val response: Response<Void> = accountService.saveAccount(
            id = id,
            password = password
        )

        if (!response.isSuccessful) {
            throw JoinErrorResponse.from(response.code())
        }
    }

    suspend fun postAccount(id: String, password: String): String {
        val response = accountService.postAccount(
            id = id,
            password = password
        )

        if (response.isSuccessful) {
            val cookie = response.headers().get(SET_COOKIE)
                ?: throw JoinErrorResponse.from(response.code())
            Log.d("woogi", "postAccount: $cookie")
            return cookie
        }
        throw IOException()
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
