package com.digginroom.digginroom.repository

import com.digginroom.digginroom.model.room.Room

interface RoomRepository {

    suspend fun findNext(): Result<Room>

    suspend fun scrap(): Result<Unit>
    suspend fun cancelScrap()
    fun updateWeightById(id: Long, value: Double)
}
