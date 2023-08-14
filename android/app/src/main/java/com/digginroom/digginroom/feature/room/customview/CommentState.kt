package com.digginroom.digginroom.feature.room.customview

sealed class CommentState {
    sealed class Create() : CommentState() {
        object Ready : Create()
        object Succeed : Create()
        object Failed : Create()
    }

    sealed class Edit() : CommentState() {
        object Ready : Edit()
        object Succeed : Edit()
        object Failed : Edit()
    }
}
