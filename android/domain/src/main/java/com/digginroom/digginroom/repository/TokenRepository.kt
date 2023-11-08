package com.digginroom.digginroom.repository

import com.digginroom.digginroom.logging.LogResult

interface TokenRepository {

    suspend fun save(token: String): LogResult<Unit>

    suspend fun delete(): LogResult<Unit>

    suspend fun fetch(): LogResult<String>
}
