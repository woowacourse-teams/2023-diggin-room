package com.digginroom.digginroom.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CancelScrapRequest(
    @SerialName("roomId")
    val roomId: Long
)