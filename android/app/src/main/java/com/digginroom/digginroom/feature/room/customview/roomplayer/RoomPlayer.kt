package com.digginroom.digginroom.feature.room.customview.roomplayer

import com.digginroom.digginroom.feature.room.roominfo.RoomInfoType
import com.digginroom.digginroom.feature.room.roominfo.RoomInfoUiState
import com.dygames.roompager.Adapter

interface RoomPlayer : Adapter.ViewHolder {

    val roomInfoType: RoomInfoType

    fun play()
    fun pause()
    fun navigate(roomInfoUiState: RoomInfoUiState)
}
