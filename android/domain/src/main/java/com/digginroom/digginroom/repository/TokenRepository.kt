package com.digginroom.digginroom.repository

interface TokenRepository {

    fun save(token: String)

    fun fetch(): String
}
