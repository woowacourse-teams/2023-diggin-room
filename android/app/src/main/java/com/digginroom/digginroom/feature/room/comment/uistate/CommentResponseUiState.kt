package com.digginroom.digginroom.feature.room.comment.uistate

import com.digginroom.digginroom.model.CommentItem

sealed class CommentResponseUiState {
    data class Succeed(
        val comments: List<CommentItem> = emptyList()
    ) : CommentResponseUiState()

    data class Failed(val message: String = "") : CommentResponseUiState()
    object Loading : CommentResponseUiState()
}
