package com.digginroom.digginroom.model.room

data class Room(
    val videoId: String,
    val isScrapped: Boolean,
    val track: Track,
    val roomId: Long,
    val scrapCount: Int
)
