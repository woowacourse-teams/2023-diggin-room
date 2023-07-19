package com.digginroom.model.room

import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.model.room.Song
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RoomTest {
    @Test
    fun video_id() {
        val room = Room("", Song("", "", "", emptyList(), emptyList()), true)
        assertEquals(room.videoId, "")
    }
}
