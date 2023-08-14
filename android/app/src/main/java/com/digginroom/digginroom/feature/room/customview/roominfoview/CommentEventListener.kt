package com.digginroom.digginroom.feature.room.customview.roominfoview

interface CommentEventListener {
    fun findComments()
    fun updateComment(commentId: Long, comment: String, updatedPosition: Int)
    fun deleteComment()
    fun postComment(comment: String)
}
