package com.digginroom.digginroom.data.datasource.remote

import android.util.Log
import com.digginroom.digginroom.data.entity.JoinRequest
import com.digginroom.digginroom.data.entity.JoinResponse
import com.digginroom.digginroom.data.service.AccountService
import java.io.IOException

class DefaultAccountRemoteDataSource(
    private val accountService: AccountService = NetworkModule.accountService
) : AccountRemoteDataSource {

    override suspend fun saveAccount(joinRequest: JoinRequest): JoinResponse =
        accountService.saveAccount(
            id = joinRequest.id,
            password = joinRequest.password
        )

    override suspend fun postAccount(id: String, password: String): String {
        val response = accountService.postAccount(
            id = id,
            password = password
        )

        if (response.isSuccessful) {
            val cookie = response.headers().get(SET_COOKIE) ?: throw IOException()
            Log.d("woogi", "postAccount: $cookie")
            return cookie
        }
        throw IOException()
    }

    companion object {

        private const val SET_COOKIE = "Set-Cookie"
    }
}
