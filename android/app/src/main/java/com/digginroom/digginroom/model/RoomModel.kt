package com.digginroom.digginroom.model

import java.io.Serializable

data class RoomModel(
    val videoId: String,
    val song: SongModel,
    val isScrapped: Boolean
) : Serializable
