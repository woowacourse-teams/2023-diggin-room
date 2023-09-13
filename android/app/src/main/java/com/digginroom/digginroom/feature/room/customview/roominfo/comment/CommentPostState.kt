package com.digginroom.digginroom.feature.room.customview.roominfo.comment

sealed class CommentPostState {
    object Post : CommentPostState()
    class Update(val commentId: Long) : CommentPostState()
}
