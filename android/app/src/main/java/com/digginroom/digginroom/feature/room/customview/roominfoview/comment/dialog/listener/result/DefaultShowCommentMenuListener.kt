package com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.result

import androidx.fragment.app.FragmentManager
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.CommentState
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.CommentViewModel
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.CommentDialog
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.CommentMenuDialog
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.DeleteCommentAlertDialog
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.CommentMenuEventListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.ShowCommentMenuListener
import com.digginroom.digginroom.model.CommentModel

class DefaultShowCommentMenuListener(
    private val commentMenuDialog: CommentMenuDialog,
    private val commentDialog: CommentDialog,
    private val roomId: Long,
    private val fragmentManager: FragmentManager,
    private val commentViewModel: CommentViewModel
) : ShowCommentMenuListener {
    override fun show(comment: CommentModel, selectedPosition: Int) {
        if (commentMenuDialog.isAdded) return
        commentMenuDialog.show(fragmentManager, "")
        commentMenuDialog.updateEventListener(
            object : CommentMenuEventListener {
                override fun update() {
                    commentViewModel.updateCommentState(
                        CommentState.Edit.Ready
                    )
                    commentViewModel.updateWrittenComment(comment.comment)
                    commentDialog.updateSelectedCommentId(comment.id)
                    commentDialog.updateSelectedPosition(selectedPosition)
                    commentMenuDialog.dismiss()
                }

                override fun delete() {
                    val builder = DeleteCommentAlertDialog()
                    builder.updateComment(comment)
                    builder.updateCommentMenuDialog(commentMenuDialog)
                    builder.updateRoomId(roomId)
                    builder.updateCommentViewModel(commentViewModel)
                    builder.updateSelectedPosition(selectedPosition)
                    builder.show(fragmentManager, "")
                }
            }
        )
    }
}
