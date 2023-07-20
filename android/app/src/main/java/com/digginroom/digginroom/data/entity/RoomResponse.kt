package com.digginroom.digginroom.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoomResponse(
    @SerialName("videoId")
    val videoId: String,
    @SerialName("isScrapped")
    val isScrapped: Boolean
)
