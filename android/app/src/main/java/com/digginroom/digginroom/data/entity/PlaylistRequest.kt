package com.digginroom.digginroom.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistRequest(
    @SerialName("title")
    val title: String,
    @SerialName("videoIds")
    val videosId: List<String>,
    @SerialName("code")
    val authCode: String
)
