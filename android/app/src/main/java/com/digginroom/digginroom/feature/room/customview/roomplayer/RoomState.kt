package com.digginroom.digginroom.feature.room.customview.roomplayer

import com.digginroom.digginroom.model.RoomModel

sealed class RoomState {
    data class Error(val throwable: Throwable) : RoomState()
    object Loading : RoomState()
    data class Success(val rooms: List<RoomModel>) : RoomState()
}
