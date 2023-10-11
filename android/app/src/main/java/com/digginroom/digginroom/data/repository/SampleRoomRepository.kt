package com.digginroom.digginroom.data.repository

import androidx.annotation.Keep
import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.logging.logRunCatching
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.model.room.Track
import com.digginroom.digginroom.model.room.genre.Genre
import com.digginroom.digginroom.model.room.scrap.ScrappedRoom
import com.digginroom.digginroom.model.room.scrap.playlist.Playlist
import com.digginroom.digginroom.repository.RoomRepository

class SampleRoomRepository @Keep constructor() : RoomRepository {
    override suspend fun findNext(): LogResult<Room> {
        return logRunCatching {
            Room(
                "tl0uN6aBqeQ",
                false,
                Track(
                    "Last Goodbye",
                    "Jeff Buckley",
                    Genre.ROCK,
                    """
                        "Last Goodbye"은 <Grace>의 두 번째 싱글입니다. 이 곡은 서로 사랑하지만 관계가 틀어지기 직전에 서로가 서로에게 맞지 않는다는 사실을 받아들이고 헤어지는 남자와 여자의 이야기를 담고 있습니다.
                    """.trimIndent()
                ),
                0,
                63
            )
        }
    }

    override suspend fun findScrapped(): LogResult<List<ScrappedRoom>> {
        TODO("Not yet implemented")
    }

    override suspend fun postScrapById(roomId: Long): LogResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun removeScrapById(roomId: Long): LogResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun postDislike(roomId: Long): LogResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun postPlaylist(authCode: String, playlist: Playlist): LogResult<Unit> {
        TODO("Not yet implemented")
    }
}
