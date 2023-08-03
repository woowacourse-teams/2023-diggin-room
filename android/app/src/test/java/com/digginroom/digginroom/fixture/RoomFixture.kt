package com.digginroom.digginroom.fixture

import com.digginroom.digginroom.model.room.Genre
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.model.room.Track

object RoomFixture {

    fun Room() = Room(
        videoId = "Jsm1qk2avyw",
        isScrapped = true,
        track = Track(
            title = "",
            artist = "",
            superGenre = Genre("")
        )
    )

    fun Rooms(): List<Room> = listOf(
        Room(
            videoId = "Jsm1qk2avyw1",
            isScrapped = true,
            track = Track(
                title = "",
                artist = "",
                superGenre = Genre("")
            )
        ),
        Room(
            videoId = "Jsm1qk2avyw2",
            isScrapped = true,
            track = Track(
                title = "",
                artist = "",
                superGenre = Genre("")
            )
        ),
        Room(
            videoId = "Jsm1qk2avyw3",
            isScrapped = true,
            track = Track(
                title = "",
                artist = "",
                superGenre = Genre("")
            )
        ),
        Room(
            videoId = "Jsm1qk2avyw4",
            isScrapped = true,
            track = Track(
                title = "",
                artist = "",
                superGenre = Genre("")
            )
        ),
        Room(
            videoId = "Jsm1qk2avyw5",
            isScrapped = true,
            track = Track(
                title = "",
                artist = "",
                superGenre = Genre("")
            )
        ),
        Room(
            videoId = "Jsm1qk2avyw6",
            isScrapped = true,
            track = Track(
                title = "",
                artist = "",
                superGenre = Genre("")
            )
        )
    )
}
