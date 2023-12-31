package com.digginroom.digginroom.data.repository

import androidx.annotation.Keep
import com.digginroom.digginroom.data.datasource.remote.RoomRemoteDataSource
import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.logging.logRunCatching
import com.digginroom.digginroom.model.mapper.PlaylistMapper.toEntity
import com.digginroom.digginroom.model.mapper.PlaylistMapper.toExpectingTime
import com.digginroom.digginroom.model.mapper.RoomMapper.toDomain
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.model.room.scrap.ScrappedRoom
import com.digginroom.digginroom.model.room.scrap.playlist.Playlist
import com.digginroom.digginroom.repository.ExtractionStateRepository
import com.digginroom.digginroom.repository.RoomRepository

class DefaultRoomRepository @Keep constructor(
    private val roomRemoteDataSource: RoomRemoteDataSource,
    private val extractionStateRepository: ExtractionStateRepository
) : RoomRepository {

    override suspend fun findNext(): LogResult<Room> {
        return logRunCatching {
            roomRemoteDataSource.findNext().toDomain()
        }
    }

    override suspend fun findScrapped(): LogResult<List<ScrappedRoom>> {
        return logRunCatching {
            roomRemoteDataSource.findScrapped().toDomain()
        }
    }

    override suspend fun postScrapById(roomId: Long): LogResult<Unit> {
        return logRunCatching {
            roomRemoteDataSource.postScrapById(
                roomId = roomId
            )
        }
    }

    override suspend fun removeScrapById(roomId: Long): LogResult<Unit> {
        return logRunCatching {
            roomRemoteDataSource.removeScrapById(roomId)
        }
    }

    override suspend fun postDislike(roomId: Long): LogResult<Unit> {
        return logRunCatching {
            roomRemoteDataSource.postDislike(roomId)
        }
    }

    override suspend fun postPlaylist(authCode: String, playlist: Playlist): LogResult<Unit> {
        return logRunCatching {
            extractionStateRepository.save(playlist.toExpectingTime())
            roomRemoteDataSource.postPlaylist(playlist.toEntity(authCode))
        }
    }
}
