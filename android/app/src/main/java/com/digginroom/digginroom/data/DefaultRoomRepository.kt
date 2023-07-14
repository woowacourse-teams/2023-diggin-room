package com.digginroom.digginroom.data

import com.digginroom.model.room.Room
import com.digginroom.repository.RoomRepository

class DefaultRoomRepository : RoomRepository {
    override suspend fun findNext(): Room {
        TODO("Not yet implemented")
    }

    override fun updateScrapById(id: Long, value: Boolean) {
        TODO("Not yet implemented")
    }

    override fun updateWeightById(id: Long, value: Double) {
        TODO("Not yet implemented")
    }
}
