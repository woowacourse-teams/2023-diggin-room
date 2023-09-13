package com.digginroom.digginroom.feature.room.customview.roominfo.comment.dialog

data class CommentMenuUiState(
    val update: () -> Unit,
    val delete: () -> Unit
)
