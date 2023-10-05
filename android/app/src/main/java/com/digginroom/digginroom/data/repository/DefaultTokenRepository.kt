package com.digginroom.digginroom.data.repository

import androidx.annotation.Keep
import com.digginroom.digginroom.data.datasource.local.TokenLocalDataSource
import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.logging.logRunCatching
import com.digginroom.digginroom.repository.TokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultTokenRepository @Keep constructor(
    private val tokenLocalDataSource: TokenLocalDataSource
) : TokenRepository {

    override suspend fun save(token: String): LogResult<Unit> =
        withContext(Dispatchers.IO) {
            logRunCatching {
                tokenLocalDataSource.save(token)
            }
        }

    override suspend fun delete(): LogResult<Unit> =
        withContext(Dispatchers.IO) {
            logRunCatching {
                tokenLocalDataSource.delete()
            }
        }

    override suspend fun fetch(): LogResult<String> =
        withContext(Dispatchers.IO) {
            logRunCatching {
                tokenLocalDataSource.fetch()
            }
        }
}
