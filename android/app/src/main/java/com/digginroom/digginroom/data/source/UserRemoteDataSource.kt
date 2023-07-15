package com.digginroom.digginroom.data.source

import com.digginroom.digginroom.data.entity.JoinRequest
import com.digginroom.digginroom.data.entity.JoinResponse

interface UserRemoteDataSource {

    suspend fun save(joinRequest: JoinRequest): JoinResponse
}
