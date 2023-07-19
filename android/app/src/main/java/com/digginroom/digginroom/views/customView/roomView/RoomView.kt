package com.digginroom.digginroom.views.customView.roomView

import com.digginroom.digginroom.views.model.RoomModel

interface RoomView {
    fun play()
    fun pause()
    fun navigate(room: RoomModel)
}
