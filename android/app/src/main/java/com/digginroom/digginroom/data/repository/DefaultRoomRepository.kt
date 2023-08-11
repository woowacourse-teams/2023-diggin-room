package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.data.datasource.remote.RoomRemoteDataSource
import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.logging.logRunCatching
import com.digginroom.digginroom.model.comment.Comment
import com.digginroom.digginroom.model.mapper.RoomMapper.toDomain
import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.repository.RoomRepository
import java.time.LocalDateTime

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

    override suspend fun findComments(roomId: Long): LogResult<List<Comment>> {
        return logRunCatching {
//            roomRemoteDataSource.findComments(roomId).toDomain()
            listOf(
                Comment(
                    0L,
                    "berry",
                    "안녕하세요~ 베리입니다~",
                    LocalDateTime.of(2023, 8, 11, 7, 30),
                    LocalDateTime.of(2023, 8, 11, 7, 45),
                    true
                ),
                Comment(
                    1L,
                    "berry2",
                    "안녕하세요~ 베리2입니다~",
                    LocalDateTime.of(2023, 8, 1, 7, 30),
                    LocalDateTime.of(2023, 8, 11, 7, 45),
                    true
                )
            )
        }
    }
}
