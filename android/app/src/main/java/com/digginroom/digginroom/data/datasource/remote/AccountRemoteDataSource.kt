package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.data.entity.IdDuplicationResponse
import com.digginroom.digginroom.data.entity.JoinResponse

interface AccountRemoteDataSource {

    suspend fun saveAccount(id: String, password: String): JoinResponse

    suspend fun fetchIsDuplicatedId(id: String): IdDuplicationResponse
}
