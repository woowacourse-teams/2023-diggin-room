package com.digginroom.digginroom.feature.room.customview.roominfoview

interface CommentEventListener {
    fun findComments()
    fun updateComment()
    fun deleteComment()
    fun sendComment(comment: String)
}
