package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.data.entity.JoinRequest
import com.digginroom.digginroom.data.entity.JoinResponse
import com.digginroom.digginroom.data.entity.LoginResponse

interface AccountRemoteDataSource {

    suspend fun saveAccount(joinRequest: JoinRequest): JoinResponse

    suspend fun postAccount(id: String, password: String): LoginResponse
}
