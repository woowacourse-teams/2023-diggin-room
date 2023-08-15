package com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener

import com.digginroom.digginroom.feature.room.RoomActivity
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.CommentViewModel
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.CommentDialog
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.CommentMenuDialog
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.result.DefaultShowCommentMenuListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.result.DeleteCommentResultListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.result.PostCommentResultListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.result.UpdateCommentResultListener

class DefaultShowCommentsListener(
    private val commentDialog: CommentDialog,
    val commentViewModel: CommentViewModel,
    val activity: RoomActivity
) : ShowCommentsListener {

    private var commentMenuDialog: CommentMenuDialog = CommentMenuDialog()
    override fun show(roomId: Long) {
        if (commentDialog.isAdded) return
        commentDialog.show(activity.supportFragmentManager, "")
        commentDialog.updateViewModel(commentViewModel)
        commentDialog.updateCommentEventListener(DefaultCommentEventListener(commentViewModel, roomId))
        commentDialog.updatePostCommentResultListener(
            PostCommentResultListener(activity)
        )
        commentDialog.updateUpdateCommentResultListener(
            UpdateCommentResultListener(activity)
        )
        commentDialog.updateDeleteCommentResultListener(
            DeleteCommentResultListener(activity)
        )
        commentDialog.updateShowCommentMenuListener(
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
