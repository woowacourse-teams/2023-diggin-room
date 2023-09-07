package com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.result

import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.CommentRequestState

interface RequestResultListener {
    fun handleResult(commentRequestState: CommentRequestState)
}
