package com.digginroom.digginroom.feature.room.customview.roominfoview

import com.digginroom.digginroom.model.CommentModel

interface ShowRoomCommentListener {

    fun show(trackModel: List<CommentModel>)
}
