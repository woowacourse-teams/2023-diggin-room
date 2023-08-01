package com.digginroom.digginroom.repository

import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.model.room.Room

interface RoomRepository {

    suspend fun findNext(): LogResult<Room>
    fun updateScrapById(id: Long, value: Boolean)
    fun updateWeightById(id: Long, value: Double)
}
