package com.digginroom.digginroom.views.customview.roomview

import com.digginroom.digginroom.views.model.RoomModel

interface RoomPlayer {
    fun play()
    fun pause()
    fun navigate(room: RoomModel)
}
