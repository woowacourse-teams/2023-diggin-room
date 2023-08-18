package com.digginroom.digginroom.model.room

import com.digginroom.digginroom.model.room.genre.Genre

data class Track(
    val title: String,
    val artist: String,
    val superGenre: Genre,
    val description: String
)
