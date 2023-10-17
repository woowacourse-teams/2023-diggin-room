package com.digginroom.digginroom.repository

import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.model.comment.Comment

interface CommentRepository {
    suspend fun findComments(
        roomId: Long,
        lastCommentId: Long? = null,
        size: Int
    ): LogResult<List<Comment>>

    suspend fun postComment(roomId: Long, comment: String): LogResult<Comment>
    suspend fun updateComment(roomId: Long, commentId: Long, comment: String): LogResult<Comment>
    suspend fun deleteComment(roomId: Long, commentId: Long): LogResult<Unit>
}
