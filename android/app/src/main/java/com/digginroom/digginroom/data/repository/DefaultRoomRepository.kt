package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.data.datasource.local.TokenLocalDataSource
import com.digginroom.digginroom.data.datasource.remote.RoomRemoteDataSource
import com.digginroom.digginroom.model.room.Genre
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.model.room.Song
import com.digginroom.digginroom.repository.RoomRepository
import com.digginroom.digginroom.views.model.mapper.RoomMapper.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultRoomRepository(
    private val roomRemoteDataSource: RoomRemoteDataSource,
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RoomRepository {
    override suspend fun findNext(): Result<Room> {
        return withContext(ioDispatcher) {
            runCatching {
                roomRemoteDataSource.findNext(tokenLocalDataSource.fetch()).toDomain()
            }
        }
    }

    override fun findScrapped(): List<Room> {
        return listOf(
            Room(
                videoId = "pOviO3wKfV0",
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
        )
    }

    override fun updateScrapById(id: Long, value: Boolean) {
        TODO("Not yet implemented")
    }

    override fun updateWeightById(id: Long, value: Double) {
        TODO("Not yet implemented")
    }
}
