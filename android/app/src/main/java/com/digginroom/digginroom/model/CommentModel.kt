package com.digginroom.digginroom.model

data class CommentModel(
    val id: Long,
    val writer: String,
    val comment: String,
    val createdAt: String,
    val updateAt: String,
    val isOwner: Boolean
)
