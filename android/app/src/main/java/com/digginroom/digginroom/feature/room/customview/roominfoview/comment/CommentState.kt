package com.digginroom.digginroom.feature.room.customview.roominfoview.comment

sealed class CommentState {
    sealed class Create() : CommentState() {
        object Loading : Create()
        object Ready : Create()
        object Succeed : Create()
        object Failed : Create()
    }

    sealed class Edit() : CommentState() {
        object Loading : Create()
        object Ready : Edit()
        object Succeed : Edit()
        object Failed : Edit()
    }

    sealed class Delete() : CommentState() {
        object Loading : Delete()
        object Succeed : Delete()
        object Failed : Delete()
    }
}
