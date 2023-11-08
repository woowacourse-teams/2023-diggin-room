package com.digginroom.digginroom.model.room.scrap

import com.digginroom.digginroom.model.room.Room

data class ScrappedRoom(
    val room: Room,
    val isSelected: Boolean
) {

    fun switchSelection(): ScrappedRoom = ScrappedRoom(
        room = room,
        isSelected = !isSelected
    )
}
