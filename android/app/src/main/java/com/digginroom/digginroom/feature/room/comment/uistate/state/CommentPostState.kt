package com.digginroom.digginroom.feature.room.comment.uistate.state

sealed class CommentPostState {
    object Post : CommentPostState()
    class Update(val commentId: Long) : CommentPostState()
}
