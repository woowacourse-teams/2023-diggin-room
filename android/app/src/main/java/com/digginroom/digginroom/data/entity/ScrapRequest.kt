package com.digginroom.digginroom.data.entity

import kotlinx.serialization.SerialName

data class ScrapRequest(
    @SerialName("roomId")
    val roomId: Long
)
