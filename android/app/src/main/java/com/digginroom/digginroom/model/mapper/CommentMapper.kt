package com.digginroom.digginroom.model.mapper

import com.digginroom.digginroom.data.entity.CommentResponse
import com.digginroom.digginroom.data.entity.CommentsResponse
import com.digginroom.digginroom.model.CommentModel
import com.digginroom.digginroom.model.comment.Comment
import java.time.Duration
import java.time.LocalDateTime

object CommentMapper {

    fun Comment.toModel(): CommentModel {
        val currentTime = LocalDateTime.now()
        val durationSeconds = Duration.between(createdAt, currentTime).seconds
        return CommentModel(
            id,
            writer,
            comment,
            ElapsedTimeFormatter.convert(durationSeconds),
            durationSeconds == 0L,
            isOwner
        )
    }

    fun CommentResponse.toDomain(): Comment {
        return Comment(
            id,
            writer,
            comment,
            LocalDateTime.parse(createdAt),
            LocalDateTime.parse(updatedAt),
            isOwner
        )
    }

    fun CommentsResponse.toDomain(): List<Comment> {
        return comments.map { it.toDomain() }
    }
}
