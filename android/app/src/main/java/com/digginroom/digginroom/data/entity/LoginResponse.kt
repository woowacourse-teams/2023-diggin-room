package com.digginroom.digginroom.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("token")
    val token: String
)
