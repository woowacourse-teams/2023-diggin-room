package com.digginroom.digginroom.feature.room.comment.uistate

import com.digginroom.digginroom.model.CommentItem

sealed class CommentResponseUiState {

    sealed class Succeed : CommentResponseUiState() {

        open val comments: List<CommentItem> = emptyList()

        data class Find(
            override val comments: List<CommentItem> = emptyList()
        ) : Succeed()
        data class Submit(
            override val comments: List<CommentItem> = emptyList()
        ) : Succeed()
        data class Delete(
            override val comments: List<CommentItem> = emptyList()
        ) : Succeed()
    }

    sealed class Failed : CommentResponseUiState() {
        object Find : Failed()
        object Submit : Failed()
        object Delete : Failed()
    }

    object Loading : CommentResponseUiState()
}
