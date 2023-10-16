package com.digginroom.digginroom.feature.room.roominfo

import com.digginroom.digginroom.model.TrackModel

data class RoomInfoEvent(
    val openSetting: () -> Unit,
    val openComment: (Long) -> Unit,
    val openInfo: (TrackModel) -> Unit,
    val openScrap: () -> Unit,
    val scrap: (Long) -> Unit,
    val unScrap: (Long) -> Unit,
    val copyInfo: (TrackModel) -> Unit
)
