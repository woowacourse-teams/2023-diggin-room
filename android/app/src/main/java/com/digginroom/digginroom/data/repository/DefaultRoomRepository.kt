package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.data.datasource.remote.RoomRemoteDataSource
import com.digginroom.model.room.Room
import com.digginroom.repository.RoomRepository

class DefaultRoomRepository(
    private val roomRemoteDataSource: RoomRemoteDataSource
) : RoomRepository {
    override suspend fun findNext(): Room {
        return roomRemoteDataSource.findNext()
    }

    override fun updateScrapById(id: Long, value: Boolean) {
        TODO("Not yet implemented")
    }

    override fun updateWeightById(id: Long, value: Double) {
        TODO("Not yet implemented")
    }
}
