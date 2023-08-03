package com.digginroom.digginroom.fixture

import com.digginroom.digginroom.model.room.Genre
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.model.room.Song

object RoomFixture {

    fun Room(
        videoId: String = "-hyQf_7KsPc",
        song: Song = Song(
            title = "예시 제목",
            albumTitle = "예시 앨범 타이틀",
            artist = "예시 아티스트",
            genres = listOf(
                Genre("예시 장르 1"),
                Genre("예시 장르 2"),
                Genre("예시 장르 3")
            ),
            tags = listOf(
                "예시 태그 1",
                "예시 태그 2",
                "예시 태그 3",
                "예시 태그 4",
                "예시 태그 5"
            )
        ),
        isScrapped: Boolean = true
    ) = com.digginroom.digginroom.model.room.Room(
        videoId = videoId,
        song = song,
        isScrapped = isScrapped
    )

    fun Rooms(): List<Room> = listOf(
        Room(
            videoId = "r8kxr39UVg8",
            song = Song(
                title = "예시 제목",
                albumTitle = "예시 앨범 타이틀",
                artist = "예시 아티스트",
                genres = listOf(
                    Genre("예시 장르 1"),
                    Genre("예시 장르 2"),
                    Genre("예시 장르 3")
                ),
                tags = listOf(
                    "예시 태그 1",
                    "예시 태그 2",
                    "예시 태그 3",
                    "예시 태그 4",
                    "예시 태그 5"
                )
            ),
            isScrapped = true
        ),
        Room(
            videoId = "cfES5D7CbMA",
            song = Song(
                title = "예시 제목",
                albumTitle = "예시 앨범 타이틀",
                artist = "예시 아티스트",
                genres = listOf(
                    Genre("예시 장르 1"),
                    Genre("예시 장르 2"),
                    Genre("예시 장르 3")
                ),
                tags = listOf(
                    "예시 태그 1",
                    "예시 태그 2",
                    "예시 태그 3",
                    "예시 태그 4",
                    "예시 태그 5"
                )
            ),
            isScrapped = true
        ),
        Room(
            videoId = "13aOJlKChVI",
            song = Song(
                title = "예시 제목",
                albumTitle = "예시 앨범 타이틀",
                artist = "예시 아티스트",
                genres = listOf(
                    Genre("예시 장르 1"),
                    Genre("예시 장르 2"),
                    Genre("예시 장르 3")
                ),
                tags = listOf(
                    "예시 태그 1",
                    "예시 태그 2",
                    "예시 태그 3",
                    "예시 태그 4",
                    "예시 태그 5"
                )
            ),
            isScrapped = true
        ),
        Room(
            videoId = "L6MkDEKLq0g",
            song = Song(
                title = "예시 제목",
                albumTitle = "예시 앨범 타이틀",
                artist = "예시 아티스트",
                genres = listOf(
                    Genre("예시 장르 1"),
                    Genre("예시 장르 2"),
                    Genre("예시 장르 3")
                ),
                tags = listOf(
                    "예시 태그 1",
                    "예시 태그 2",
                    "예시 태그 3",
                    "예시 태그 4",
                    "예시 태그 5"
                )
            ),
            isScrapped = true
        ),
        Room(
            videoId = "VtAieLbbxek",
            song = Song(
                title = "예시 제목",
                albumTitle = "예시 앨범 타이틀",
                artist = "예시 아티스트",
                genres = listOf(
                    Genre("예시 장르 1"),
                    Genre("예시 장르 2"),
                    Genre("예시 장르 3")
                ),
                tags = listOf(
                    "예시 태그 1",
                    "예시 태그 2",
                    "예시 태그 3",
                    "예시 태그 4",
                    "예시 태그 5"
                )
            ),
            isScrapped = true
        )
    )
}
