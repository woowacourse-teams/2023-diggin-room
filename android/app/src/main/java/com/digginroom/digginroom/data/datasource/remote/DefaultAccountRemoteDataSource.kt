package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.data.entity.JoinRequest
import com.digginroom.digginroom.data.entity.JoinResponse
import com.digginroom.digginroom.data.entity.LoginResponse
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

    override suspend fun postAccount(id: String, password: String): LoginResponse {
        val response = accountService.postAccount(
            id = id,
            password = password
        )

        if (response.isSuccessful) {
            return response.body() ?: throw IOException()
        }
        throw IOException()
    }
}
