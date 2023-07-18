package com.digginroom.repository

import com.digginroom.model.room.Room

interface RoomRepository {

    suspend fun findNext(): Result<Room>
    fun updateScrapById(id: Long, value: Boolean)
    fun updateWeightById(id: Long, value: Double)
}
