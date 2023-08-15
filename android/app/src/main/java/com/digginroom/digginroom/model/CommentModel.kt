package com.digginroom.digginroom.model

data class CommentModel(
    val id: Long,
    val writer: String,
    val comment: String,
    val elapsedTime: String,
    val isUpdated: Boolean,
    val isOwner: Boolean
)
