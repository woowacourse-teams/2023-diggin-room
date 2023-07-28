package com.digginroom.digginroom.views.model

data class SongModel(
    val title: String,
    val albumTitle: String,
    val artist: String,
    val genres: List<GenreModel>,
    val tags: List<String>
)
