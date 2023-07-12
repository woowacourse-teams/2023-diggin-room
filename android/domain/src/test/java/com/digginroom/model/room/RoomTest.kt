package com.digginroom.model.room

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RoomTest {
    @Test
    fun video_id() {
        val room = Room("", Song("", "", "", emptyList(), emptyList()), true)
        assertEquals(room.videoId, "")
    }
}
