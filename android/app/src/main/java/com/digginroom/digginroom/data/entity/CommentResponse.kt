package com.digginroom.digginroom.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentResponse(
    @SerialName("writer")
    val writer: String,
    @SerialName("comment")
    val comment: String,
    @SerialName("publishedTimeText")
    val publishedTimeText: String,
    @SerialName("isOwner")
    val isOwner: Boolean
)
