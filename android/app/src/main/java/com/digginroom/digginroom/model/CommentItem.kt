package com.digginroom.digginroom.model

sealed class CommentItem {
    data class CommentModel(
        val id: Long,
        val writer: String,
        val comment: String,
        val elapsedTime: String,
        val isOwner: Boolean
    ) : CommentItem()

    object Loading : CommentItem()
}
