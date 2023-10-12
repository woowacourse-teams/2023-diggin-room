package com.digginroom.digginroom.feature.room.comment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.DialogCommentBottomPlacedItemLayoutBinding
import com.digginroom.digginroom.databinding.DialogCommentLayoutBinding
import com.digginroom.digginroom.feature.room.comment.CommentViewModel
import com.digginroom.digginroom.feature.room.comment.adapter.CommentAdapter
import com.digginroom.digginroom.feature.room.comment.uistate.CommentResponseUiState
import com.digginroom.digginroom.model.CommentModel
import com.dygames.androiddi.ViewModelDependencyInjector.injectViewModel

class CommentDialog : BottomFixedItemBottomSheetDialog() {

    private lateinit var dialogBinding: DialogCommentLayoutBinding
    private lateinit var bottomPlacedItemBinding: DialogCommentBottomPlacedItemLayoutBinding
    private val commentViewModel: CommentViewModel by lazy {
        ViewModelProvider(
            this,
            injectViewModel<CommentViewModel>()
        )[CommentViewModel::class.java]
    }

    override val dialogView: View by lazy { dialogBinding.root }
    override val bottomFixedItemView: View by lazy { bottomPlacedItemBinding.root }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialogStyle)
    }

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
        showNow(fragmentManager, "CommentDialog")
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
        ).show(parentFragmentManager, "CommentMenuDialog")
    }
}
