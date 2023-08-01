package com.digginroom.digginroom.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScrappedRoom(
    @SerialName("roomId")
    val roomId: Long,
    @SerialName("videoId")
    val videoId: String
)
