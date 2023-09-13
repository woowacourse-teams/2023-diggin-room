package com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.result

import androidx.fragment.app.FragmentManager
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.CommentViewModel
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.CommentDialog
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.CommentMenuDialog
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
//        commentMenuDialog.updateEventListener(object : CommentMenuEventListener {
//            override fun update() {
//                commentViewModel.startUpdatingComment()
//                commentViewModel.updateWrittenComment(comment.comment)
//                commentDialog.updateSelectedCommentId(comment.id)
//                commentDialog.updateSelectedPosition(selectedPosition)
//                commentMenuDialog.dismiss()
//            }
//
//            override fun delete() {
//                val builder = DeleteCommentAlertDialog({
//                    commentViewModel.deleteComment(roomId, comment.id, selectedPosition)
//                }) {
//                    if (commentMenuDialog.isAdded) commentMenuDialog.dismiss()
//                }
//                builder.show(fragmentManager, "")
//            }
//        })
    }
}
