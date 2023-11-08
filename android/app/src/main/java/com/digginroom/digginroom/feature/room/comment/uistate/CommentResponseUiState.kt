package com.digginroom.digginroom.feature.room.comment.uistate

sealed class CommentResponseUiState {

    sealed class Succeed : CommentResponseUiState() {

        open val comments: List<CommentUiState> = emptyList()

        data class Find(
            override val comments: List<CommentUiState> = emptyList()
        ) : Succeed()
        data class Submit(
            override val comments: List<CommentUiState> = emptyList()
        ) : Succeed()
        data class Delete(
            override val comments: List<CommentUiState> = emptyList()
        ) : Succeed()
    }

    sealed class Failed : CommentResponseUiState() {
        object Find : Failed()
        object Submit : Failed()
        object Delete : Failed()
    }

    object Loading : CommentResponseUiState()
}
