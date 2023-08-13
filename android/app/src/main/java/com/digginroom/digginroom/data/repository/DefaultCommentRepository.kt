package com.digginroom.digginroom.data.repository

import com.digginroom.digginroom.data.datasource.remote.CommentRemoteDataSource
import com.digginroom.digginroom.logging.LogResult
import com.digginroom.digginroom.logging.logRunCatching
import com.digginroom.digginroom.model.comment.Comment
import com.digginroom.digginroom.repository.CommentRepository
import java.time.LocalDateTime

class DefaultCommentRepository(private val commentRemoteDataSource: CommentRemoteDataSource) :
    CommentRepository {

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

    override suspend fun postComment(roomId: Long, comment: String): LogResult<Unit> {
        return logRunCatching {
            commentRemoteDataSource.postComment(roomId, comment)
        }
    }
}
