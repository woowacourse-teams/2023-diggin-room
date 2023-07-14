package com.digginroom.digginroom.views

import com.digginroom.model.room.Room

interface RoomView {
    fun play()
    fun pause()
    fun navigate(room: Room)
}
