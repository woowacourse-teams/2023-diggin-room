package com.digginroom.digginroom.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemberResponse(
    @SerialName("memberId")
    val memberId: Long,
    @SerialName("username")
    val userName: String,
    @SerialName("hasFavorite")
    val hasSurveyed: Boolean
)
