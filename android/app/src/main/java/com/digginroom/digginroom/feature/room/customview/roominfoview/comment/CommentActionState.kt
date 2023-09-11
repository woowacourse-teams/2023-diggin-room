package com.digginroom.digginroom.feature.room.customview.roominfoview.comment

sealed interface CommentActionState {
    object Post : CommentActionState
    object Update : CommentActionState
}
