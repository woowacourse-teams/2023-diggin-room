package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.data.datasource.remote.RoomRemoteDataSource
import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.logging.logRunCatching
import com.digginroom.digginroom.model.mapper.RoomMapper.toDomain
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.repository.RoomRepository

class DefaultRoomRepository(
    private val roomRemoteDataSource: RoomRemoteDataSource
) : RoomRepository {

    override suspend fun findNext(): LogResult<Room> {
        return logRunCatching {
            roomRemoteDataSource.findNext().toDomain()
        }
    }

    override suspend fun findScrapped(): LogResult<List<Room>> {
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
}
