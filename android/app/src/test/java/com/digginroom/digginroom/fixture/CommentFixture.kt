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

    fun Comments(): List<Comment> = listOf(
        Comment(
            id = 0,
            writer = "test2",
            comment = "노래 좋아요2",
            createdAt = LocalDateTime.of(2023, 8, 31, 11, 39),
            updateAt = LocalDateTime.of(2023, 8, 31, 11, 39),
            isOwner = true
        ),
        Comment(
            id = 0,
            writer = "test3",
            comment = "노래 좋아요3",
            createdAt = LocalDateTime.of(2023, 8, 31, 11, 39),
            updateAt = LocalDateTime.of(2023, 8, 31, 11, 39),
            isOwner = true
        ),
        Comment(
            id = 0,
            writer = "test4",
            comment = "노래 좋아요4",
            createdAt = LocalDateTime.of(2023, 8, 31, 11, 39),
            updateAt = LocalDateTime.of(2023, 8, 31, 11, 39),
            isOwner = true
        )
    )
}
