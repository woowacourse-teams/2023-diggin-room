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

    override suspend fun scrapById(roomId: Long): LogResult<Unit> {
        return logRunCatching {
            roomRemoteDataSource.scrapById(tokenLocalDataSource.fetch(), roomId)
        }
    }

    override suspend fun unScrapById(roomId: Long): LogResult<Unit> {
        TODO("Not yet implemented")
    }

    override fun updateWeightById(id: Long, value: Double) {
        TODO("Not yet implemented")
    }
}
