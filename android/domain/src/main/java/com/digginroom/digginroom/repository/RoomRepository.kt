package com.digginroom.digginroom.repository

import com.digginroom.digginroom.model.room.Room

interface RoomRepository {

    suspend fun findNext(): Result<Room>
    suspend fun findScrapped(): Result<List<Room>>
    fun updateScrapById(id: Long, value: Boolean)
    fun updateWeightById(id: Long, value: Double)
}
