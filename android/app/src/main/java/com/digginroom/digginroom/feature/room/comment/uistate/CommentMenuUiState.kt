package com.digginroom.digginroom.feature.room.comment.uistate

data class CommentMenuUiState(
    val update: () -> Unit,
    val delete: () -> Unit
)
