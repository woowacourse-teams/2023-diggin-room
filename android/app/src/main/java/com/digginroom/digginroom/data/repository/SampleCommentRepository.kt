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
        val comments = (COMMENT_START_INDEX..COMMENT_END_INDEX).map {
            Comment(
                it.toLong(),
                ('a' + it).toString(),
                ('a' + it).toString(),
                localDateTime,
                localDateTime,
                true
            )
        }
        val startIndex =
            adjustIndexToSize(comments.size, calculateStartIndex(comments, lastCommentId))
        val endIndex = adjustIndexToSize(comments.size, calculateEndIndex(startIndex, size))
        val returnComments = comments.subList(
            startIndex,
            endIndex
        )
        return logRunCatching {
            if (lastCommentId == COMMENT_END_INDEX.toLong()) emptyList() else returnComments
        }
    }

    private fun calculateStartIndex(comments: List<Comment>, lastCommentId: Long?) =
        comments.indexOf(comments.first { it.id == (lastCommentId ?: 0) }) + 1

    private fun calculateEndIndex(startIndex: Int, size: Int?) = startIndex + (size ?: 10)

    private fun adjustIndexToSize(size: Int, index: Int) = if (index >= size) size else index

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

    companion object {
        private const val COMMENT_START_INDEX = 0
        private const val COMMENT_END_INDEX = 30
    }
}
