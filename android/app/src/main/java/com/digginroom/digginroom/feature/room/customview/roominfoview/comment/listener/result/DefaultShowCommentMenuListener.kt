package com.digginroom.digginroom.feature.room.customview.roominfoview.comment.listener.result

import com.digginroom.digginroom.feature.room.RoomActivity
import com.digginroom.digginroom.feature.room.customview.CommentState
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.CommentViewModel
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.CommentDialog
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.CommentMenuDialog
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.DeleteCommentAlertDialog
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.listener.CommentMenuEventListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.listener.ShowCommentMenuListener
import com.digginroom.digginroom.model.CommentModel

class DefaultShowCommentMenuListener(
    private val commentMenuDialog: CommentMenuDialog,
    private val commentDialog: CommentDialog,
    private val roomId: Long,
    private val activity: RoomActivity,
    private val commentViewModel: CommentViewModel
) : ShowCommentMenuListener {
    override fun show(comment: CommentModel, selectedPosition: Int) {
        if (commentMenuDialog.isAdded) return
        commentMenuDialog.show(activity.supportFragmentManager, "")
        commentMenuDialog.updateEventListener(
            object : CommentMenuEventListener {
                override fun update() {
                    commentViewModel.updateCommentState(
                        CommentState.Edit.Ready
                    )
                    commentViewModel.updateComment(comment.comment)
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
                    builder.show(activity.supportFragmentManager, "")
                }
            }
        )
        commentMenuDialog.updateSelectedComment(comment)
    }
}
