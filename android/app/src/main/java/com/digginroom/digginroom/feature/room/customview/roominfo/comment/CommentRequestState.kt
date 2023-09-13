package com.digginroom.digginroom.feature.room.customview.roominfo.comment

sealed interface CommentRequestState {
    object Loading : CommentRequestState
    object Done : CommentRequestState
    object Succeed : CommentRequestState
    data class Failed(val message: String = "") : CommentRequestState
}
