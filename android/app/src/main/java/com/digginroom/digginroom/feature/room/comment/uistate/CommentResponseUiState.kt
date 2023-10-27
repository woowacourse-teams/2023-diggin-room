package com.digginroom.digginroom.feature.room.comment.uistate

import com.digginroom.digginroom.model.CommentItem

sealed class CommentResponseUiState {
    data class Succeed(
        val comments: List<CommentItem> = emptyList()
    ) : CommentResponseUiState()

    sealed class Failed : CommentResponseUiState() {
        object Find : Failed()
        object Submit : Failed()
        object Delete : Failed()
    }

    object Loading : CommentResponseUiState()
}
