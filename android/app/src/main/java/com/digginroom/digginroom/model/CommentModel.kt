package com.digginroom.digginroom.model

data class CommentModel(
    val writer: String,
    val comment: String,
    val publishedTimeText: String,
    val isOwner: Boolean
)
