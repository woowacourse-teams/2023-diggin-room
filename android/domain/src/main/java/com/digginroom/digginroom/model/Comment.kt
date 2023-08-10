package com.digginroom.digginroom.model

data class Comment(
    val writer: String,
    val comment: String,
    val publishedTimeText: String,
    val isOwner: Boolean
)
