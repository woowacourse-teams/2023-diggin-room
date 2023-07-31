package com.digginroom.digginroom.repository

interface TokenRepository {

    suspend fun save(token: String): Result<Unit>

    suspend fun fetch(): Result<String>
}
