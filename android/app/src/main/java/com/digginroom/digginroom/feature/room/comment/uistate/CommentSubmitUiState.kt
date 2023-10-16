package com.digginroom.digginroom.feature.room.comment.uistate

data class CommentSubmitUiState(
    val delete: (Long, Long) -> Unit = { _, _ -> },
    val changeToUpdate: () -> Unit = { },
    val state: SubmitState = SubmitState.POST
)
