package com.digginroom.digginroom.views.customView.roomview

import com.digginroom.digginroom.views.model.RoomModel

sealed class RoomState {
    data class Error(val throwable: Throwable) : RoomState()
    object Loading : RoomState()
    data class Success(val rooms: List<RoomModel>) : RoomState()
}
