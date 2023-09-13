package com.digginroom.digginroom.feature.room.customview.roominfo.comment.dialog.listener

import com.digginroom.digginroom.feature.room.customview.roominfo.comment.CommentActionState

interface CommentEventListener {
    fun findComments()
    fun updateComment(commentId: Long, comment: String, updatedPosition: Int)
    fun deleteComment(commentId: Long, updatedPosition: Int)
    fun postComment(comment: String)
    fun updateState(commentState: CommentActionState)
}
