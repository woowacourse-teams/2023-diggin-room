package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.data.entity.IdDuplicationResponse

interface AccountRemoteDataSource {

    suspend fun saveAccount(id: String, password: String): Unit

    suspend fun fetchIsDuplicatedId(id: String): IdDuplicationResponse
}
