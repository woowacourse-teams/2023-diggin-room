package com.digginroom.digginroom.feature.room.comment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.databinding.DialogCommentBottomPlacedItemLayoutBinding
import com.digginroom.digginroom.databinding.DialogCommentLayoutBinding
import com.digginroom.digginroom.feature.room.comment.CommentViewModel
import com.digginroom.digginroom.feature.room.comment.adapter.CommentAdapter
import com.digginroom.digginroom.feature.room.comment.uistate.CommentResponseUiState
import com.digginroom.digginroom.model.CommentItem
import com.dygames.di.DependencyInjector.inject

class CommentDialog : BottomFixedItemBottomSheetDialog() {

    private lateinit var dialogBinding: DialogCommentLayoutBinding
    private lateinit var bottomPlacedItemBinding: DialogCommentBottomPlacedItemLayoutBinding
    private val commentViewModel: CommentViewModel = inject()

    override val dialogView: View by lazy { dialogBinding.root }
    override val bottomFixedItemView: View by lazy { bottomPlacedItemBinding.root }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        initDialogBinding()
        initBottomPlacedItemBinding()
        setUpObserver()
        isCancelable = true
        return dialogBinding.root
    }

    private fun initDialogBinding() {
        dialogBinding = DialogCommentLayoutBinding.inflate(LayoutInflater.from(context))
        dialogBinding.lifecycleOwner = this
        dialogBinding.commentViewModel = commentViewModel
        dialogBinding.adapter = CommentAdapter(::showCommentMenuDialog)
        dialogBinding.dialogCommentRecyclerViewComment.setHasFixedSize(true)
    }

    private fun initBottomPlacedItemBinding() {
        bottomPlacedItemBinding =
            DialogCommentBottomPlacedItemLayoutBinding.inflate(LayoutInflater.from(context))
        bottomPlacedItemBinding.commentViewModel = commentViewModel
    }

    fun show(fragmentManager: FragmentManager, roomId: Long) {
        if (isAdded) return
        showNow(fragmentManager, COMMENT_DIALOG_TAG)
        commentViewModel.findComments(roomId, COMMENT_SIZE)
        dialogBinding.roomId = roomId
        bottomPlacedItemBinding.roomId = roomId
        setUpRecyclerViewListener(roomId)
    }

    private fun setUpRecyclerViewListener(roomId: Long) {
        dialogBinding.dialogCommentRecyclerViewComment.apply {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!canScrollVertically(1)) {
                        commentViewModel.findComments(roomId, COMMENT_SIZE)
                    }
                }
            })
        }
    }

    private fun setUpObserver() {
        commentViewModel.commentResponseUiState.observe(this) {
            when (it) {
                is CommentResponseUiState.Failed -> Unit

                is CommentResponseUiState.Loading -> Unit

                is CommentResponseUiState.Succeed -> {
                    bottomPlacedItemBinding.currentComment = ""
                    dialogBinding.dialogCommentRecyclerViewComment.apply {
                        (adapter as? CommentAdapter)?.submitList(it.comments)
                        smoothScrollToPosition(0)
                        visibility = if (it.comments.isEmpty()) {
                            View.GONE
                        } else {
                            View.VISIBLE
                        }
                    }
                    (dialogBinding.dialogCommentRecyclerViewComment.adapter as? CommentAdapter)?.submitList(
                        it.comments
                    )
                    dialogBinding.dialogCommentTvDefault.visibility = if (it.comments.isEmpty()) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                }
            }
        }
    }

    private fun showCommentMenuDialog(comment: CommentItem.CommentModel) {
        CommentMenuDialog(
            roomId = dialogBinding.roomId ?: return, commentId = comment.id, updateComment = {
                bottomPlacedItemBinding.currentComment = comment.comment
                bottomPlacedItemBinding.updateTargetComment = comment
            }, commentSubmitUiState = commentViewModel.commentSubmitUiState.value ?: return
        ).show(parentFragmentManager, COMMENT_MENU_DIALOG_TAG)
    }

    companion object {
        private const val COMMENT_DIALOG_TAG = "CommentDialog"
        private const val COMMENT_MENU_DIALOG_TAG = "CommentMenuDialog"
        private const val COMMENT_SIZE = 10
    }
}
