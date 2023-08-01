package com.digginroom.digginroom.feature.room.customview.roomplayer

import com.digginroom.digginroom.model.RoomModel

interface RoomPlayer {
    fun play()
    fun pause()
    fun navigate(room: RoomModel)
}
