package com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener

import android.content.Context
import androidx.fragment.app.FragmentManager
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
    private val fragmentManager: FragmentManager,
    private val context: Context
) : ShowCommentsListener {

    private var commentMenuDialog: CommentMenuDialog = CommentMenuDialog()
    override fun show(roomId: Long) {
        if (commentDialog.isAdded) return
        commentDialog.show(fragmentManager, "")
        commentDialog.updateViewModel(commentViewModel)
        commentDialog.updateCommentEventListener(DefaultCommentEventListener(commentViewModel, roomId))
        commentDialog.updatePostCommentResultListener(
            PostCommentResultListener(context)
        )
        commentDialog.updateUpdateCommentResultListener(
            UpdateCommentResultListener(context)
        )
        commentDialog.updateDeleteCommentResultListener(
            DeleteCommentResultListener(context)
        )
        commentDialog.updateShowCommentMenuListener(
            DefaultShowCommentMenuListener(
                commentMenuDialog,
                commentDialog,
                roomId,
                fragmentManager,
                commentViewModel
            )
        )
    }
}
