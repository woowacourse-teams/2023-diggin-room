package com.digginroom.digginroom

import com.digginroom.digginroom.model.room.Genre
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.model.room.Song

object RoomFixture {

    fun createRooms(): List<Room> = List(5) {
        Room(
            videoId = "-hyQf_7KsPc",
            song = Song(
                title = "예시 제목",
                albumTitle = "예시 앨범 타이틀",
                artist = "예시 아티스트",
                genres = List(3) {
                    Genre("예시 장르$it")
                },
                tags = List(5) {
                    "예시 태그$it"
                }
            ),
            isScrapped = true
        )
    }
}
