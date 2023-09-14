package com.digginroom.digginroom.feature.room.comment.uistate.state

import com.digginroom.digginroom.model.CommentModel

sealed interface CommentState {
    data class Failed(val message: String = "") : CommentState
    object Loading : CommentState
    data class Succeed(val comments: List<CommentModel>) : CommentState
}
