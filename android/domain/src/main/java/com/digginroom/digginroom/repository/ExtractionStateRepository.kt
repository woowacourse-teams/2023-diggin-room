package com.digginroom.digginroom.repository

import com.digginroom.digginroom.logging.LogResult

interface ExtractionStateRepository {

    suspend fun save(expectedTime: Long): LogResult<Unit>

    suspend fun fetch(): LogResult<Boolean>
}
