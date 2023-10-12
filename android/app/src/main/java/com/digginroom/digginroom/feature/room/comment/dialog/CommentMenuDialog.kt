package com.digginroom.digginroom.feature.room.comment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.digginroom.digginroom.databinding.DialogCommentMenuLayoutBinding
import com.digginroom.digginroom.feature.room.comment.uistate.CommentSubmitUiState
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommentMenuDialog(
    private val roomId: Long,
    private val commentId: Long,
    private val updateComment: () -> Unit,
    private val commentSubmitUiState: CommentSubmitUiState
) : BottomSheetDialogFragment() {
    lateinit var binding: DialogCommentMenuLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCommentMenuLayoutBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.dialogCommentMenuUpdate.setOnClickListener {
            updateCommentPostState()
        }
        binding.dialogCommentMenuDelete.setOnClickListener {
            showCommentDeleteAlertDialog()
        }
        isCancelable = true
        return binding.root
    }

    private fun updateCommentPostState() {
        updateComment()
        commentSubmitUiState.changeToUpdate()
        dismiss()
    }

    private fun showCommentDeleteAlertDialog() {
        CommentDeleteAlertDialog(roomId, commentId, commentSubmitUiState) {
            dismiss()
        }.show(parentFragmentManager, DELETE_COMMENT_ALERT_DIALOG_TAG)
    }
    companion object {
        private const val DELETE_COMMENT_ALERT_DIALOG_TAG = "DeleteCommentAlertDialog"
    }
}
