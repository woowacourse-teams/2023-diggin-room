package com.digginroom.digginroom.fixture

import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.model.room.Track
import com.digginroom.digginroom.model.room.genre.Genre

object RoomFixture {

    fun Room() = Room(
        videoId = "Jsm1qk2avyw",
        isScrapped = true,
        track = Track(
            title = "예시 제목1",
            artist = "예시 아티스트1",
            superGenre = Genre.SOUNDS_AND_EFFECTS
        ),
        roomId = 0
    )

    fun Rooms(): List<Room> = listOf(
        Room(
            videoId = "Jsm1qk2avyw1",
            isScrapped = true,
            track = Track(
                title = "예시 제목2",
                artist = "예시 아티스트2",
                superGenre = Genre.SOUNDS_AND_EFFECTS
            ),
            roomId = 0
        ),
        Room(
            videoId = "Jsm1qk2avyw2",
            isScrapped = true,
            track = Track(
                title = "예시 제목3",
                artist = "예시 아티스트3",
                superGenre = Genre.SOUNDS_AND_EFFECTS
            ),
            roomId = 0
        ),
        Room(
            videoId = "Jsm1qk2avyw3",
            isScrapped = true,
            track = Track(
                title = "예시 제목4",
                artist = "예시 아티스트4",
                superGenre = Genre.SOUNDS_AND_EFFECTS
            ),
            roomId = 0
        ),
        Room(
            videoId = "Jsm1qk2avyw4",
            isScrapped = true,
            track = Track(
                title = "예시 제목5",
                artist = "예시 아티스트5",
                superGenre = Genre.SOUNDS_AND_EFFECTS
            ),
            roomId = 0
        ),
        Room(
            videoId = "Jsm1qk2avyw5",
            isScrapped = true,
            track = Track(
                title = "예시 제목6",
                artist = "예시 아티스트6",
                superGenre = Genre.SOUNDS_AND_EFFECTS
            ),
            roomId = 0
        ),
        Room(
            videoId = "Jsm1qk2avyw6",
            isScrapped = true,
            track = Track(
                title = "예시 제목7",
                artist = "예시 아티스트7",
                superGenre = Genre.SOUNDS_AND_EFFECTS
            ),
            roomId = 0
        )
    )
}
