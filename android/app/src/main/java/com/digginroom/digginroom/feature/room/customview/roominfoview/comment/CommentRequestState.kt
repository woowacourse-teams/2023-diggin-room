package com.digginroom.digginroom.feature.room.customview.roominfoview.comment

sealed interface CommentRequestState {
    object Loading : CommentRequestState
    object Done : CommentRequestState
}
