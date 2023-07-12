package com.digginroom.model.room

data class Song(
    val title: String,
    val albumTitle: String,
    val artist: String,
    val genres: List<Genre>,
    val tags: List<String>
)
