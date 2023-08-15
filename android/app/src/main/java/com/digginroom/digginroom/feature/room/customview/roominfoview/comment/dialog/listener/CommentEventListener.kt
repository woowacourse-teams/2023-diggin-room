package com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener

interface CommentEventListener {
    fun findComments()
    fun updateComment(commentId: Long, comment: String, updatedPosition: Int)
    fun deleteComment(commentId: Long, updatedPosition: Int)

    fun postComment(comment: String)
}
