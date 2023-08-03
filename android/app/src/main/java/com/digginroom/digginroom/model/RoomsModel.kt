package com.digginroom.digginroom.model

import java.io.Serializable

data class RoomsModel(
    val value: List<RoomModel> = listOf()
) : Serializable
