package com.digginroom.digginroom.data.repository

import androidx.annotation.Keep
import com.digginroom.digginroom.data.datasource.remote.CommentRemoteDataSource
import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.logging.logRunCatching
import com.digginroom.digginroom.model.comment.Comment
import com.digginroom.digginroom.model.mapper.CommentMapper.toDomain
import com.digginroom.digginroom.repository.CommentRepository

class DefaultCommentRepository @Keep constructor(
    private val commentRemoteDataSource: CommentRemoteDataSource
) : CommentRepository {

    override suspend fun findComments(
        roomId: Long,
        lastCommentId: Long?,
        size: Int
    ): LogResult<List<Comment>> {
        return logRunCatching {
            commentRemoteDataSource.findComments(roomId, lastCommentId, size).toDomain()
        }
    }

    override suspend fun postComment(roomId: Long, comment: String): LogResult<Comment> {
        return logRunCatching {
            commentRemoteDataSource.postComment(roomId, comment).toDomain()
        }
    }

    override suspend fun updateComment(
        roomId: Long,
        commentId: Long,
        comment: String
    ): LogResult<Comment> {
        return logRunCatching {
            commentRemoteDataSource.updateComment(roomId, commentId, comment).toDomain()
        }
    }

    override suspend fun deleteComment(roomId: Long, commentId: Long): LogResult<Unit> {
        return logRunCatching {
            commentRemoteDataSource.deleteComment(roomId, commentId)
        }
    }
}
