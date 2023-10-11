package com.digginroom.digginroom.model

data class ScrappedRoomModel(
    val room: RoomModel,
    val isSelected: Boolean,
    val selectable: Boolean
)
