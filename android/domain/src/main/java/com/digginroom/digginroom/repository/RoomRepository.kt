package com.digginroom.digginroom.repository

import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.model.comment.Comment
import com.digginroom.digginroom.model.room.Room

interface RoomRepository {

    suspend fun findNext(): LogResult<Room>
    suspend fun findScrapped(): LogResult<List<Room>>
    suspend fun postScrapById(roomId: Long): LogResult<Unit>
    suspend fun removeScrapById(roomId: Long): LogResult<Unit>
    suspend fun postDislike(roomId: Long): LogResult<Unit>
    suspend fun findComments(roomId: Long): LogResult<List<Comment>>
}
