package com.digginroom.digginroom.repository

interface TokenRepository {

    fun save(token: String): Result<Unit>

    fun fetch(): Result<String>
}
