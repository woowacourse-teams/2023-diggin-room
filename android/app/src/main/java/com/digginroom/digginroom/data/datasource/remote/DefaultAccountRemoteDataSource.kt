package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.data.entity.IdDuplicationResponse
import com.digginroom.digginroom.data.entity.JoinError
import com.digginroom.digginroom.data.entity.JoinRequest
import com.digginroom.digginroom.data.entity.JoinResponse
import com.digginroom.digginroom.data.service.AccountService
import retrofit2.Response

class DefaultAccountRemoteDataSource(
    private val accountService: AccountService = NetworkModule.accountService
) : AccountRemoteDataSource {

    override suspend fun saveAccount(joinRequest: JoinRequest): JoinResponse {
        val response: Response<JoinResponse> = accountService.saveAccount(
            id = joinRequest.id,
            password = joinRequest.password
        )

        if (response.isSuccessful) {
            return response.body() ?: throw JoinError.from(response.code())
        }
        throw JoinError.from(response.code())
    }

    override suspend fun fetchIsDuplicatedId(id: String): IdDuplicationResponse {
        val response: Response<IdDuplicationResponse> = accountService.fetchIsDuplicatedId(id)

        if (response.isSuccessful) {
            return response.body() ?: throw JoinError.from(response.code())
        }
        throw JoinError.from(response.code())
    }
}
