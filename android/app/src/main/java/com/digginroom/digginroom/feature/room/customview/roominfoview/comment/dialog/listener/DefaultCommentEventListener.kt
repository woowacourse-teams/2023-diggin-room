package com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener

import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.CommentState
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.CommentViewModel

class DefaultCommentEventListener(val commentViewModel: CommentViewModel, val roomId: Long) :
    CommentEventListener {
    override fun findComments() {
        commentViewModel.findComments(roomId)
    }

    override fun postComment(comment: String) {
        commentViewModel.postComment(roomId, comment)
    }

    override fun updateState(commentState: CommentState) {
        commentViewModel.updateCommentState(commentState)
    }

    override fun updateComment(
        commentId: Long,
        comment: String,
        updatedPosition: Int
    ) {
        commentViewModel.updateWrittenComment(roomId, commentId, comment, updatedPosition)
    }

    override fun deleteComment(
        commentId: Long,
        updatedPosition: Int
    ) {
        commentViewModel.deleteComment(roomId, commentId, updatedPosition)
    }
}
