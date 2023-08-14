package com.digginroom.digginroom.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackResponse(
    @SerialName("title")
    val title: String,
    @SerialName("artist")
    val artist: String,
    @SerialName("superGenre")
    val superGenre: String,
    @SerialName("description")
    val description: String
)
