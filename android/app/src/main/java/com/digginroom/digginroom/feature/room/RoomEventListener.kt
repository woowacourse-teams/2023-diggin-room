package com.digginroom.digginroom.feature.room

fun interface RoomEventListener {
    fun event(roomId: Long)
}
