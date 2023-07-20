package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.data.datasource.local.TokenLocalDataSource
import com.digginroom.digginroom.repository.TokenRepository

class DefaultTokenRepository(
    private val tokenLocalDataSource: TokenLocalDataSource
) : TokenRepository {

    override fun save(token: String) {
        tokenLocalDataSource.save(token)
    }

    override fun fetch(): String = tokenLocalDataSource.fetch()
}
