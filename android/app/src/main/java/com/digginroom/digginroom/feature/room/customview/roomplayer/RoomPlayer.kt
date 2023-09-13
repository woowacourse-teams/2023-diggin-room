package com.digginroom.digginroom.feature.room.customview.roomplayer

import com.digginroom.digginroom.feature.room.customview.roominfo.RoomInfoUiState
import com.dygames.roompager.Adapter

interface RoomPlayer : Adapter.ViewHolder {
    fun play()
    fun pause()
    fun navigate(roomInfoUiState: RoomInfoUiState)
}
