package com.digginroom.digginroom.feature.room.comment.uistate

data class CommentDeleteAlertUiState(
    val deleteComment: () -> Unit,
    val dismissParentDialog: () -> Unit
)
