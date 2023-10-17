package com.digginroom.digginroom.feature.room.comment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.digginroom.digginroom.databinding.DialogCommentBottomPlacedItemLayoutBinding
import com.digginroom.digginroom.databinding.DialogCommentLayoutBinding
import com.digginroom.digginroom.feature.room.comment.CommentViewModel
import com.digginroom.digginroom.feature.room.comment.adapter.CommentAdapter
import com.digginroom.digginroom.feature.room.comment.uistate.CommentResponseUiState
import com.digginroom.digginroom.model.CommentModel
import com.dygames.di.DependencyInjector.inject

class CommentDialog : BottomFixedItemBottomSheetDialog() {

    private lateinit var dialogBinding: DialogCommentLayoutBinding
    private lateinit var bottomPlacedItemBinding: DialogCommentBottomPlacedItemLayoutBinding
    private val commentViewModel: CommentViewModel = inject()

    override val dialogView: View by lazy { dialogBinding.root }
    override val bottomFixedItemView: View by lazy { bottomPlacedItemBinding.root }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initDialogBinding()
        initBottomPlacedItemBinding()
        isCancelable = true
        return dialogBinding.root
    }

    private fun initDialogBinding() {
        dialogBinding = DialogCommentLayoutBinding.inflate(LayoutInflater.from(context))
        dialogBinding.lifecycleOwner = this
        dialogBinding.commentViewModel = commentViewModel
        dialogBinding.adapter = CommentAdapter(::showCommentMenuDialog)
    }

    private fun initBottomPlacedItemBinding() {
        bottomPlacedItemBinding =
            DialogCommentBottomPlacedItemLayoutBinding.inflate(LayoutInflater.from(context))
        bottomPlacedItemBinding.commentViewModel = commentViewModel
        commentViewModel.commentResponseUiState.observe(this) {
            if (it is CommentResponseUiState.Succeed) bottomPlacedItemBinding.currentComment = ""
        }
    }

    fun show(fragmentManager: FragmentManager, id: Long) {
        if (isAdded) return
        showNow(fragmentManager, COMMENT_DIALOG_TAG)
        dialogBinding.commentViewModel?.findComments(id)
        dialogBinding.roomId = id
        bottomPlacedItemBinding.roomId = id
    }

    private fun showCommentMenuDialog(comment: CommentModel) {
        CommentMenuDialog(
            roomId = dialogBinding.roomId ?: return,
            commentId = comment.id,
            updateComment = {
                bottomPlacedItemBinding.currentComment = comment.comment
                bottomPlacedItemBinding.updateTargetComment = comment
            },
            commentSubmitUiState = commentViewModel.commentSubmitUiState.value ?: return
        ).show(parentFragmentManager, COMMENT_MENU_DIALOG_TAG)
    }

    companion object {
        private const val COMMENT_DIALOG_TAG = "CommentDialog"
        private const val COMMENT_MENU_DIALOG_TAG = "CommentMenuDialog"
    }
}
