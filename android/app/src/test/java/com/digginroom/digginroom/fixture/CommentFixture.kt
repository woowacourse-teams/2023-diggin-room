package com.digginroom.digginroom.fixture

import com.digginroom.digginroom.model.comment.Comment
import java.time.LocalDateTime

object CommentFixture {

    fun Comment(
        id: Long = 0,
        writer: String = "test",
        comment: String = "노래 좋아요",
        createdAt: LocalDateTime = LocalDateTime.of(2023, 8, 31, 11, 39),
        updateAt: LocalDateTime = LocalDateTime.of(2023, 8, 31, 11, 39),
        isOwner: Boolean = true
    ) = com.digginroom.digginroom.model.comment.Comment(
        id = id,
        writer = writer,
        comment = comment,
        createdAt = createdAt,
        updateAt = updateAt,
        isOwner = isOwner
    )

    fun Comments(): List<Comment> =
        (0..10).map {
            com.digginroom.digginroom.model.comment.Comment(
                it.toLong(),
                ("test$it").toString(),
                ("노래 좋아요$it").toString(),
                LocalDateTime.of(2023, 8, 31, 11, 39),
                LocalDateTime.of(2023, 8, 31, 11, 39),
                true
            )
        }
}
