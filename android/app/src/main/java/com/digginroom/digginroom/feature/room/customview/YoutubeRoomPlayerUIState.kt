package com.digginroom.digginroom.feature.room.customview

import com.digginroom.digginroom.feature.room.RoomEventListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.ShowRoomInfoListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.ShowCommentsListener

data class YoutubeRoomPlayerUIState(
    val onScrap: RoomEventListener,
    val onRemove: RoomEventListener,
    val showRoomInfo: ShowRoomInfoListener,
    val showComments: ShowCommentsListener,
)
