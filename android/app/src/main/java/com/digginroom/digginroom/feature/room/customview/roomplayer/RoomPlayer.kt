package com.digginroom.digginroom.feature.room.customview.roomplayer

import com.digginroom.digginroom.model.RoomModel
import com.dygames.roompager.Adapter

interface RoomPlayer : Adapter.ViewHolder {
    fun play()
    fun pause()
    fun navigate(room: RoomModel)
}
