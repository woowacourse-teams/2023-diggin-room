package com.digginroom.digginroom.model

import java.io.Serializable

data class SongModel(
    val title: String,
    val albumTitle: String,
    val artist: String,
    val genres: List<GenreModel>,
    val tags: List<String>
) : Serializable
