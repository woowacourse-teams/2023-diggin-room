package com.digginroom.digginroom.feature.room.roominfo

import com.digginroom.digginroom.model.RoomModel

data class RoomInfoUiState(
    val roomModel: RoomModel,
    val roomInfoEvent: RoomInfoEvent
)
