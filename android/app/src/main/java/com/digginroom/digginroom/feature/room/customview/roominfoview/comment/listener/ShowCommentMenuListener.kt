package com.digginroom.digginroom.feature.room.customview.roominfoview.comment.listener

import com.digginroom.digginroom.model.CommentModel

fun interface ShowCommentMenuListener {
    fun show(comment: CommentModel, selectedPosition: Int)
}
