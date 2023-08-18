package com.digginroom.digginroom.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentResponse(

    @SerialName("id")
    val id: Long,
    @SerialName("writer")
    val writer: String,
    @SerialName("comment")
    val comment: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("updatedAt")
    val updatedAt: String,
    @SerialName("isOwner")
    val isOwner: Boolean
)
