package com.digginroom.digginroom.model.comment

import java.time.LocalDateTime

data class Comment(
    val id: Long,
    val writer: String,
    val comment: String,
    val createdAt: LocalDateTime,
    val updateAt: LocalDateTime,
    val isOwner: Boolean
)
