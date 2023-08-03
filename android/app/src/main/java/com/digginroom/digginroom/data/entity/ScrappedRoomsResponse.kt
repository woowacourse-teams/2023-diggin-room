package com.digginroom.digginroom.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScrappedRoomsResponse(
    @SerialName("scrappedRooms")
    val scrappedRooms: List<RoomResponse>
)
