package com.digginroom.digginroom.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentsResponse(
    @SerialName("comments")
    val comments: List<CommentResponse>
)
