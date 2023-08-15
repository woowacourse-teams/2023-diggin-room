package com.digginroom.digginroom.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresTasteRequest(
    @SerialName("favoriteGenres")
    val genresTaste: List<String>
)
