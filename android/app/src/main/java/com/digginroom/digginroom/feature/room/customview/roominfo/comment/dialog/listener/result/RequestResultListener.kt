package com.digginroom.digginroom.feature.room.customview.roominfo.comment.dialog.listener.result

import com.digginroom.digginroom.feature.room.customview.roominfo.comment.CommentRequestState

interface RequestResultListener {
    fun handleResult(commentRequestState: CommentRequestState?)
}
