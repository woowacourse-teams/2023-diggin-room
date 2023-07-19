package com.digginroom.digginroom.views.customView.roomview

import com.digginroom.digginroom.views.model.RoomModel

interface RoomView {
    fun play()
    fun pause()
    fun navigate(room: RoomModel)
}
