package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.logging.logRunCatching
import com.digginroom.digginroom.model.comment.Comment
import com.digginroom.digginroom.repository.CommentRepository
import java.time.LocalDateTime

class SampleCommentRepository : CommentRepository {
    override suspend fun findComments(
        roomId: Long,
        lastCommentId: Long?,
        size: Int?
    ): LogResult<List<Comment>> {
        val localDateTime = LocalDateTime.of(2023, 10, 25, 3, 10)
        val comments =
            (0..20).map {
                Comment(
                    it.toLong(),
                    ('a' + it).toString(),
                    ('a' + it).toString(),
                    localDateTime,
                    localDateTime,
                    true
                )
            }
        val lastCommentIndex = comments.indexOf(comments.first { it.id == (lastCommentId ?: 0) })
        return logRunCatching {
            comments.subList(
                lastCommentIndex,
                lastCommentIndex + (size ?: 10)
            )
        }
    }

    override suspend fun postComment(roomId: Long, comment: String): LogResult<Comment> {
        TODO("Not yet implemented")
    }

    override suspend fun updateComment(
        roomId: Long,
        commentId: Long,
        comment: String
    ): LogResult<Comment> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteComment(roomId: Long, commentId: Long): LogResult<Unit> {
        TODO("Not yet implemented")
    }
}
