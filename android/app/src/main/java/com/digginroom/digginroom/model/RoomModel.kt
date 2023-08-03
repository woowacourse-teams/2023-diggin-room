package com.digginroom.digginroom.model

import java.io.Serializable

data class RoomModel(
    val videoId: String,
    val isScrapped: Boolean,
    val trackModel: TrackModel
) : Serializable
