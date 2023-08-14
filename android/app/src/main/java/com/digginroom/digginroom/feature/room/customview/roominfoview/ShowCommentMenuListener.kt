package com.digginroom.digginroom.feature.room.customview.roominfoview

import com.digginroom.digginroom.model.CommentModel

fun interface ShowCommentMenuListener {
    fun show(comment: CommentModel, selectedPosition: Int)
}
