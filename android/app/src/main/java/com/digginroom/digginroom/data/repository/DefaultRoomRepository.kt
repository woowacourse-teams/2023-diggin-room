package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.data.datasource.remote.RoomRemoteDataSource
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.repository.RoomRepository
import com.digginroom.digginroom.views.model.mapper.RoomMapper.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultRoomRepository(
    private val roomRemoteDataSource: RoomRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RoomRepository {
    override suspend fun findNext(): Result<Room> {
        return withContext(ioDispatcher) {
            runCatching {
                roomRemoteDataSource.findNext().toDomain()
            }
        }
    }

    override fun updateScrapById(id: Long, value: Boolean) {
        TODO("Not yet implemented")
    }

    override fun updateWeightById(id: Long, value: Double) {
        TODO("Not yet implemented")
    }
}
