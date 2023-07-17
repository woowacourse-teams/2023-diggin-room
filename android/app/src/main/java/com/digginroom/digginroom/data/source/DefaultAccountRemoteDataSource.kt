package com.digginroom.digginroom.data.source

import com.digginroom.digginroom.data.NetworkModule
import com.digginroom.digginroom.data.entity.JoinRequest
import com.digginroom.digginroom.data.entity.JoinResponse
import com.digginroom.digginroom.data.service.AccountService

class DefaultAccountRemoteDataSource(
    private val accountService: AccountService = NetworkModule.accountService
) : AccountRemoteDataSource {

    override suspend fun saveAccount(joinRequest: JoinRequest): JoinResponse =
        accountService.saveAccount(
            id = joinRequest.id,
            password = joinRequest.password
        )
}
