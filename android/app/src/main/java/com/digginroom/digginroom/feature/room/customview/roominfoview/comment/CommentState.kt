package com.digginroom.digginroom.feature.room.customview.roominfoview.comment

sealed interface CommentState {

    object Loading : CommentState
    sealed interface Create : CommentState {
        object Ready : Create
        object Succeed : Create
        object Failed : Create
    }

    sealed interface Edit : CommentState {
        object Ready : Edit
        object Succeed : Edit
        object Failed : Edit
    }

    sealed interface Delete : CommentState {
        object Loading : Delete
        object Succeed : Delete
        object Failed : Delete
    }
}
