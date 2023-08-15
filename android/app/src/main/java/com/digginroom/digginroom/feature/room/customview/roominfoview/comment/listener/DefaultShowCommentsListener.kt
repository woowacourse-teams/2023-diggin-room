package com.digginroom.digginroom.feature.room.customview.roominfoview.comment.listener

import com.digginroom.digginroom.feature.room.RoomActivity
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.CommentViewModel
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.CommentDialog
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.CommentMenuDialog
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.listener.result.DefaultShowCommentMenuListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.listener.result.DeleteCommentResultListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.listener.result.PostCommentResultListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.listener.result.UpdateCommentResultListener

class DefaultShowCommentsListener(
    private val commentDialog: CommentDialog,
    private val commentMenuDialog: CommentMenuDialog,
    val commentViewModel: CommentViewModel,
    val activity: RoomActivity
) : ShowCommentsListener {
    override fun show(roomId: Long) {
        if (commentDialog.isAdded) return
        commentDialog.show(activity.supportFragmentManager, "")
        commentDialog.setViewModel(commentViewModel)
        commentDialog.setCommentEventListener(DefaultCommentEventListener(commentViewModel, roomId))
        commentDialog.setPostCommentResultListener(
            PostCommentResultListener(activity)
        )
        commentDialog.setUpdateCommentResultListener(
            UpdateCommentResultListener(activity)
        )
        commentDialog.setDeleteCommentResultListener(
            DeleteCommentResultListener(activity)
        )
        commentDialog.setShowCommentMenuListener(
            DefaultShowCommentMenuListener(
                commentMenuDialog,
                commentDialog,
                roomId,
                activity,
                commentViewModel
            )
        )
    }
}
