package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.data.datasource.local.TokenLocalDataSource
import com.digginroom.digginroom.repository.TokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultTokenRepository(
    private val tokenLocalDataSource: TokenLocalDataSource
) : TokenRepository {

    override suspend fun save(token: String) =
        withContext(Dispatchers.IO) {
            runCatching {
                tokenLocalDataSource.save(token)
            }
        }

    override suspend fun fetch(): Result<String> =
        withContext(Dispatchers.IO) {
            runCatching {
                tokenLocalDataSource.fetch()
            }
        }
}
