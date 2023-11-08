package com.digginroom.digginroom.data.entity

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class PlaylistResponse(
    @SerialName("count")
    val count: Int,
    @SerialName("success")
    val success: Int
)
