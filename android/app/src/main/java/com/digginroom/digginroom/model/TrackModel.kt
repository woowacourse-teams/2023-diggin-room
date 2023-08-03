package com.digginroom.digginroom.model

import java.io.Serializable

data class TrackModel(
    val title: String,
    val artist: String,
    val superGenre: String
) : Serializable
