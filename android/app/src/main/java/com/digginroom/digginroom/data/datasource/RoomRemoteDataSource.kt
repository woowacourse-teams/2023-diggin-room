package com.digginroom.digginroom.data.datasource

import com.digginroom.model.room.Room
import com.digginroom.model.room.Song

class RoomRemoteDataSource {
    fun findNext(): Room {
        return Room(
            "ucZl6vQ_8Uo",
            Song("", "", "", emptyList(), emptyList()),
            false
        )
    }
}
