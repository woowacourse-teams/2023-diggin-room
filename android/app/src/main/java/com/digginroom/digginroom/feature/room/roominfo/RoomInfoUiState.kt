package com.digginroom.digginroom.feature.room.roominfo

import com.digginroom.digginroom.model.RoomModel
import com.digginroom.digginroom.model.TrackModel

data class RoomInfoUiState(
    val roomModel: RoomModel,
    val openComment: (Long) -> Unit,
    val openInfo: (TrackModel) -> Unit,
    val openScrap: () -> Unit,
    val scrap: (Long) -> Unit,
    val unScrap: (Long) -> Unit
)
