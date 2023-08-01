package com.digginroom.digginroom.model

data class RoomModel(
    val videoId: String,
    val song: SongModel,
    val isScrapped: Boolean,
)
