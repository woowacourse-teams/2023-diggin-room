package com.digginroom.digginroom.model

data class SongModel(
    val title: String,
    val albumTitle: String,
    val artist: String,
    val genres: List<GenreModel>,
    val tags: List<String>,
)
