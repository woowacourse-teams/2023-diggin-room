package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.data.datasource.local.TokenLocalDataSource
import com.digginroom.digginroom.data.datasource.remote.RoomRemoteDataSource
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.repository.RoomRepository
import com.digginroom.digginroom.views.model.mapper.RoomMapper.toDomain

class DefaultRoomRepository(
    private val roomRemoteDataSource: RoomRemoteDataSource,
    private val tokenLocalDataSource: TokenLocalDataSource
) : RoomRepository {

    override suspend fun findNext(): Result<Room> =
        runCatching {
            roomRemoteDataSource.findNext(tokenLocalDataSource.fetch()).toDomain()
        }

    override suspend fun findScrapped(): Result<List<Room>> =
        runCatching {
            roomRemoteDataSource.findScrapped()
        }

    override fun updateScrapById(id: Long, value: Boolean) {
        TODO("Not yet implemented")
    }

    override fun updateWeightById(id: Long, value: Double) {
        TODO("Not yet implemented")
    }
}
