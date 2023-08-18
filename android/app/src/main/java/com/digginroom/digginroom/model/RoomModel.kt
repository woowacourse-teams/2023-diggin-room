package com.digginroom.digginroom.model

import java.io.Serializable

data class RoomModel(
    val videoId: String,
    val isScrapped: Boolean,
    val track: TrackModel,
    val roomId: Long,
    val scrapCount: Int
) : Serializable
