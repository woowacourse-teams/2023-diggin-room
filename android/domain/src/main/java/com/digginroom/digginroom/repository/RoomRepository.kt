package com.digginroom.digginroom.repository

import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.model.room.Room

interface RoomRepository {

    suspend fun findNext(): LogResult<Room>
    suspend fun scrapById(roomId: Long): LogResult<Unit>
    suspend fun unScrapById(roomId: Long): LogResult<Unit>
    fun updateWeightById(id: Long, value: Double)
}
