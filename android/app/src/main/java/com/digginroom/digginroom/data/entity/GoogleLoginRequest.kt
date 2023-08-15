package com.digginroom.digginroom.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleLoginRequest(
    @SerialName("idToken")
    val idToken: String
)
