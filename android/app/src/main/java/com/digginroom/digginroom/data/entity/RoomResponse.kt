package com.digginroom.digginroom.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoomResponse(
    @SerialName("roomId")
    val roomId: Long,
    @SerialName("videoId")
    val videoId: String,
    @SerialName("isScrapped")
    val isScrapped: Boolean,
    @SerialName("track")
    val track: TrackResponse,
    @SerialName("scrapCount")
    val scrapCount: Int
)
