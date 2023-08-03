package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.data.datasource.local.TokenLocalDataSource
import com.digginroom.digginroom.data.datasource.remote.RoomRemoteDataSource
import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.logging.logRunCatching
import com.digginroom.digginroom.model.mapper.RoomMapper.toDomain
import com.digginroom.digginroom.model.room.Genre
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.model.room.Track
import com.digginroom.digginroom.repository.RoomRepository

class DefaultRoomRepository(
    private val roomRemoteDataSource: RoomRemoteDataSource,
    private val tokenLocalDataSource: TokenLocalDataSource
) : RoomRepository {
    override suspend fun findNext(): LogResult<Room> {
        return logRunCatching {
            roomRemoteDataSource.findNext(tokenLocalDataSource.fetch()).toDomain()
        }
    }

    override suspend fun findScrapped(): Result<List<Room>> {
        val rooms = listOf(
            Room(
                videoId = "-hyQf_7KsPc",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "_x3IbCecuuk",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "ovlka1CxCrY",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "Jsm1qk2avyw",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "-hyQf_7KsPc",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "_x3IbCecuuk",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "ovlka1CxCrY",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "Jsm1qk2avyw",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "-hyQf_7KsPc",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "_x3IbCecuuk",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "ovlka1CxCrY",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            ),
            Room(
                videoId = "Jsm1qk2avyw",
                isScrapped = true,
                track = Track(
                    title = "",
                    artist = "",
                    superGenre = Genre("")
                ),
                roomId = 0
            )
        )
        return Result.success(rooms)
    }

    override suspend fun scrapById(roomId: Long): LogResult<Unit> {
        return logRunCatching {
            roomRemoteDataSource.scrapById(tokenLocalDataSource.fetch(), roomId)
        }.onSuccess {}.onFailure {}
    }

    override suspend fun cancelScrapById(roomId: Long): LogResult<Unit> {
        return logRunCatching {
            roomRemoteDataSource.cancelScrapById(tokenLocalDataSource.fetch(), roomId)
        }
    }

    override fun updateWeightById(id: Long, value: Double) {
        TODO("Not yet implemented")
    }
}
