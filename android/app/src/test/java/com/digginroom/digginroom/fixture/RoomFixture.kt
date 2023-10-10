package com.digginroom.digginroom.fixture

import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.model.room.Track
import com.digginroom.digginroom.model.room.genre.Genre
import com.digginroom.digginroom.model.room.scrap.ScrappedRoom

object RoomFixture {

    fun Room(
        videoId: String = "Jsm1qk2avyw",
        isScrapped: Boolean = true,
        track: Track = Track(
            title = "예시 제목1",
            artist = "예시 아티스트1",
            superGenre = Genre.SOUNDS_AND_EFFECTS,
            description = ""
        ),
        roomId: Long = 0,
        scrapCount: Int = 0
    ) = com.digginroom.digginroom.model.room.Room(
        videoId = videoId,
        isScrapped = isScrapped,
        track = track,
        roomId = roomId,
        scrapCount = scrapCount
    )

    fun Rooms(): List<Room> = listOf(
        Room(
            videoId = "Jsm1qk2avyw1",
            isScrapped = true,
            track = Track(
                title = "예시 제목2",
                artist = "예시 아티스트2",
                superGenre = Genre.SOUNDS_AND_EFFECTS,
                description = ""
            ),
            roomId = 0,
            scrapCount = 0
        ),
        Room(
            videoId = "Jsm1qk2avyw2",
            isScrapped = true,
            track = Track(
                title = "예시 제목3",
                artist = "예시 아티스트3",
                superGenre = Genre.SOUNDS_AND_EFFECTS,
                description = ""
            ),
            roomId = 0,
            scrapCount = 0
        ),
        Room(
            videoId = "Jsm1qk2avyw3",
            isScrapped = true,
            track = Track(
                title = "예시 제목4",
                artist = "예시 아티스트4",
                superGenre = Genre.SOUNDS_AND_EFFECTS,
                description = ""
            ),
            roomId = 0,
            scrapCount = 0
        ),
        Room(
            videoId = "Jsm1qk2avyw4",
            isScrapped = true,
            track = Track(
                title = "예시 제목5",
                artist = "예시 아티스트5",
                superGenre = Genre.SOUNDS_AND_EFFECTS,
                description = ""
            ),
            roomId = 0,
            scrapCount = 0
        ),
        Room(
            videoId = "Jsm1qk2avyw5",
            isScrapped = true,
            track = Track(
                title = "예시 제목6",
                artist = "예시 아티스트6",
                superGenre = Genre.SOUNDS_AND_EFFECTS,
                description = ""
            ),
            roomId = 0,
            scrapCount = 0
        ),
        Room(
            videoId = "Jsm1qk2avyw6",
            isScrapped = true,
            track = Track(
                title = "예시 제목7",
                artist = "예시 아티스트7",
                superGenre = Genre.SOUNDS_AND_EFFECTS,
                description = ""
            ),
            roomId = 0,
            scrapCount = 0
        )
    )

    fun ScrappedRooms(): List<ScrappedRoom> = listOf(
        ScrappedRoom(
            Room(
                videoId = "Jsm1qk2avyw1",
                isScrapped = true,
                track = Track(
                    title = "예시 제목2",
                    artist = "예시 아티스트2",
                    superGenre = Genre.SOUNDS_AND_EFFECTS,
                    description = ""
                ),
                roomId = 0,
                scrapCount = 0
            ),
            false
        ),
        ScrappedRoom(
            Room(
                videoId = "Jsm1qk2avyw2",
                isScrapped = true,
                track = Track(
                    title = "예시 제목3",
                    artist = "예시 아티스트3",
                    superGenre = Genre.SOUNDS_AND_EFFECTS,
                    description = ""
                ),
                roomId = 0,
                scrapCount = 0
            ),
            false
        ),
        ScrappedRoom(
            Room(
                videoId = "Jsm1qk2avyw3",
                isScrapped = true,
                track = Track(
                    title = "예시 제목4",
                    artist = "예시 아티스트4",
                    superGenre = Genre.SOUNDS_AND_EFFECTS,
                    description = ""
                ),
                roomId = 0,
                scrapCount = 0
            ),
            false
        ),
        ScrappedRoom(
            Room(
                videoId = "Jsm1qk2avyw4",
                isScrapped = true,
                track = Track(
                    title = "예시 제목5",
                    artist = "예시 아티스트5",
                    superGenre = Genre.SOUNDS_AND_EFFECTS,
                    description = ""
                ),
                roomId = 0,
                scrapCount = 0
            ),
            false
        ),
        ScrappedRoom(
            Room(
                videoId = "Jsm1qk2avyw5",
                isScrapped = true,
                track = Track(
                    title = "예시 제목6",
                    artist = "예시 아티스트6",
                    superGenre = Genre.SOUNDS_AND_EFFECTS,
                    description = ""
                ),
                roomId = 0,
                scrapCount = 0
            ),
            false
        ),
        ScrappedRoom(
            Room(
                videoId = "Jsm1qk2avyw6",
                isScrapped = true,
                track = Track(
                    title = "예시 제목7",
                    artist = "예시 아티스트7",
                    superGenre = Genre.SOUNDS_AND_EFFECTS,
                    description = ""
                ),
                roomId = 0,
                scrapCount = 0
            ),
            false
        )
    )

    const val SELECTED_ROOM_POSITION = 0
}
