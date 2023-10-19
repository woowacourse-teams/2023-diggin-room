package com.digginroom.model.room

import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.model.room.Track
import com.digginroom.digginroom.model.room.genre.Genre
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RoomTest {
    @Test
    fun video_id() {
        val room = Room(
            videoId = "",
            isScrapped = true,
            track = Track(
                title = "",
                artist = "",
                superGenre = Genre.AMBIENT,
                description = ""
            ),
            roomId = 0,
            scrapCount = 0
        )
        assertEquals(room.videoId, "")
    }
}
