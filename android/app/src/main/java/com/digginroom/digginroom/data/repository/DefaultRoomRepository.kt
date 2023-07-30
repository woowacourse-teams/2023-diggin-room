package com.digginroom.digginroom.data.repository

import android.util.Log
import com.digginroom.digginroom.data.datasource.local.TokenLocalDataSource
import com.digginroom.digginroom.data.datasource.remote.RoomRemoteDataSource
import com.digginroom.digginroom.model.room.Room
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

    override suspend fun scrap(): Result<Unit> {
        return withContext(ioDispatcher) {
            runCatching {
                roomRemoteDataSource.scrap(tokenLocalDataSource.fetch())
            }
        }
    }

    override suspend fun cancelScrap() {
        TODO("Not yet implemented")
    }

    override fun updateWeightById(id: Long, value: Double) {
        TODO("Not yet implemented")
    }
}
