package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.data.datasource.local.TokenLocalDataSource
import com.digginroom.digginroom.data.datasource.remote.RoomRemoteDataSource
import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.logging.logRunCatching
import com.digginroom.digginroom.model.mapper.RoomMapper.toDomain
import com.digginroom.digginroom.model.room.Room
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

    override suspend fun findScrapped(): LogResult<List<Room>> {
        return logRunCatching {
            roomRemoteDataSource.findScrapped(tokenLocalDataSource.fetch()).toDomain()
        }
    }

    override suspend fun postScrapById(roomId: Long): LogResult<Unit> {
        return logRunCatching {
            roomRemoteDataSource.postScrapById(
                cookie = tokenLocalDataSource.fetch(),
                roomId = roomId
            )
        }
    }

    override suspend fun removeScrapById(roomId: Long): LogResult<Unit> {
        return logRunCatching {
            roomRemoteDataSource.removeScrapById(tokenLocalDataSource.fetch(), roomId)
        }
    }

    override suspend fun postDislike(roomId: Long): LogResult<Unit> {
        return logRunCatching {
            roomRemoteDataSource.postDislike(tokenLocalDataSource.fetch(), roomId)
        }
    }
}
