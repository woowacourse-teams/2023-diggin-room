package com.digginroom.digginroom.feature.room.comment.uistate

sealed class CommentUiState {
    data class CommentModel(
        val id: Long,
        val writer: String,
        val comment: String,
        val elapsedTime: String,
        val isOwner: Boolean
    ) : CommentUiState()

    object Loading : CommentUiState()
}
