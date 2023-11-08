package com.digginroom.digginroom.data.repository

import androidx.annotation.Keep
import com.digginroom.digginroom.data.datasource.local.ExtractionStateDataSource
import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.logging.logRunCatching
import com.digginroom.digginroom.repository.ExtractionStateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultExtractionStateRepository @Keep constructor(
    private val extractionStateDataSource: ExtractionStateDataSource
) : ExtractionStateRepository {

    override suspend fun save(expectedTime: Long): LogResult<Unit> =
        withContext(Dispatchers.IO) {
            logRunCatching {
                extractionStateDataSource.save(expectedTime)
            }
        }

    override suspend fun fetch(): LogResult<Boolean> =
        withContext(Dispatchers.IO) {
            logRunCatching {
                extractionStateDataSource.fetchIsAvailable()
            }
        }
}
