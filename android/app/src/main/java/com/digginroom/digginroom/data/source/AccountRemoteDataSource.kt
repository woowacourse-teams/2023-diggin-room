package com.digginroom.digginroom.data.source

import com.digginroom.digginroom.data.entity.JoinRequest
import com.digginroom.digginroom.data.entity.JoinResponse

interface AccountRemoteDataSource {

    suspend fun saveAccount(joinRequest: JoinRequest): JoinResponse
}
