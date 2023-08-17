package com.digginroom.digginroom.feature.room.customview.roominfoview.comment

sealed interface CommentState {

    sealed interface Post : CommentState {
        object Ready : Post
        object Loading : Post
        object Succeed : Post
        object Failed : Post
    }

    sealed interface Update : CommentState {
        object Ready : Update
        object Loading : Update
        object Succeed : Update
        object Failed : Update
    }

    sealed interface Delete : CommentState {
        object Ready : Delete
        object Loading : Delete
        object Succeed : Delete
        object Failed : Delete
    }
}
