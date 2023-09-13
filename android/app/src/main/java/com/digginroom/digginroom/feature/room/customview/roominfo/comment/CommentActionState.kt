package com.digginroom.digginroom.feature.room.customview.roominfo.comment

sealed interface CommentActionState {
    object Post : CommentActionState
    object Update : CommentActionState
}
