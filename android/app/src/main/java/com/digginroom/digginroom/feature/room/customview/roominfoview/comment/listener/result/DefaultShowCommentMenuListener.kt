package com.digginroom.digginroom.feature.room.customview.roominfoview.comment.listener.result

import androidx.appcompat.app.AlertDialog
import com.digginroom.digginroom.feature.room.RoomActivity
import com.digginroom.digginroom.feature.room.customview.CommentState
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.CommentViewModel
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.CommentDialog
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.CommentMenuDialog
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
        commentMenuDialog.setEventListener(object : CommentMenuEventListener {
            override fun update() {
                commentViewModel.updateCommentState(
                    CommentState.Edit.Ready
                )
                commentViewModel.setComment(comment.comment)
                commentDialog.setSelectedCommentId(comment.id)
                commentDialog.setSelectedPosition(selectedPosition)
                commentMenuDialog.dismiss()
            }

            override fun delete() {
                val builder = AlertDialog.Builder(activity)
                builder.setMessage("정말 삭제하사겠습니까?")
                    .setPositiveButton("삭제") { _, id ->
                        commentViewModel.deleteComment(
                            roomId,
                            comment.id,
                            selectedPosition
                        )
                        if (commentMenuDialog.isAdded) commentMenuDialog.dismiss()
                    }
                    .setNegativeButton("취소") { _, id ->
                        if (commentMenuDialog.isAdded) commentMenuDialog.dismiss()
                    }
                // Create the AlertDialog object and return it
                builder.create()
                builder.show()
            }
        })
        commentMenuDialog.updateComment(comment)
    }
}
