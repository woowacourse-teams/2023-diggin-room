package com.digginroom.digginroom.feature.room.customview.roominfoview

interface CommentEventListener {
    fun findComments()
    fun updateComment(comment: String)
    fun deleteComment()
    fun postComment(comment: String)
}
