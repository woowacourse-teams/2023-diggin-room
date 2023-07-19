package com.digginroom.digginroom.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IdDuplicationResponse(
    @SerialName("isDuplicated")
    val isDuplicated: Boolean
)
