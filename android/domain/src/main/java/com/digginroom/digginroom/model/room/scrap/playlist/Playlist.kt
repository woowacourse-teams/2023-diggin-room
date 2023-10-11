package com.digginroom.digginroom.model.room.scrap.playlist

data class Playlist(
    val title: Title,
    val description: String,
    val videosId: List<String>
)
