package com.digginroom.digginroom.model.mapper

import com.digginroom.digginroom.data.entity.CommentResponse
import com.digginroom.digginroom.data.entity.CommentsResponse
import com.digginroom.digginroom.model.Comment
import com.digginroom.digginroom.model.CommentModel

object CommentMapper {

    fun CommentModel.toDomain(): Comment {
        return Comment(
            writer,
            comment,
            publishedTimeText,
            isOwner
        )
    }

    fun Comment.toModel(): CommentModel {
        return CommentModel(
            writer,
            comment,
            publishedTimeText,
            isOwner
        )
    }

    fun CommentResponse.toDomain(): Comment {
        return Comment(
            writer,
            comment,
            publishedTimeText,
            isOwner
        )
    }

    fun CommentsResponse.toDomain(): List<Comment> {
        return comments.map { it.toDomain() }
    }
}
