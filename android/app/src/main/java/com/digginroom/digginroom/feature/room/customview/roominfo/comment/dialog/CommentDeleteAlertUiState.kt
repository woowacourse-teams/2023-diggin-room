package com.digginroom.digginroom.feature.room.customview.roominfo.comment.dialog

data class CommentDeleteAlertUiState(
    val deleteComment: () -> Unit,
    val dismissParentDialog: () -> Unit
)
