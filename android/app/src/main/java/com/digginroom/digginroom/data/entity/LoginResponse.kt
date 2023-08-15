package com.digginroom.digginroom.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("memberId")
    val memberId: Long,
    @SerialName("hasFavorite")
    val hasFavorite: Boolean
)
