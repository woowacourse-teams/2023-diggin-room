package com.digginroom.digginroom.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScrapRequest(
    @SerialName("roomId")
    val roomId: Long
)
