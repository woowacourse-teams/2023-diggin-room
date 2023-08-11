package com.digginroom.digginroom.model.mapper

import com.digginroom.digginroom.data.entity.CommentResponse
import com.digginroom.digginroom.data.entity.CommentsResponse
import com.digginroom.digginroom.feature.room.customview.roominfoview.ElapsedTimeFormatter
import com.digginroom.digginroom.model.CommentModel
import com.digginroom.digginroom.model.comment.Comment
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object CommentMapper {

    fun Comment.toModel(): CommentModel {
        val currentTime = LocalDateTime.now()
        val durationSeconds = Duration.between(currentTime, createdAt).seconds
        return CommentModel(
            id,
            writer,
            comment,
            ElapsedTimeFormatter.convert(durationSeconds),
            true,
            isOwner
        )
    }

    fun CommentResponse.toDomain(): Comment {
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        return Comment(
            id,
            writer,
            comment,
            LocalDateTime.parse(createdAt, formatter),
            LocalDateTime.parse(updateAt, formatter),
            isOwner
        )
    }

    fun CommentsResponse.toDomain(): List<Comment> {
        return comments.map { it.toDomain() }
    }
}
