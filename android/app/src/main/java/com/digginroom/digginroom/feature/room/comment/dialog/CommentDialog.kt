package com.digginroom.digginroom.feature.room.comment.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.DialogCommentEditTextBinding
import com.digginroom.digginroom.databinding.DialogCommentLayoutBinding
import com.digginroom.digginroom.feature.room.comment.CommentViewModel
import com.digginroom.digginroom.feature.room.comment.adapter.CommentAdapter
import com.digginroom.digginroom.feature.room.comment.uistate.CommentMenuUiState
import com.digginroom.digginroom.feature.room.comment.uistate.state.CommentPostState
import com.digginroom.digginroom.model.CommentModel
import com.dygames.androiddi.ViewModelDependencyInjector.injectViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommentDialog : BottomSheetDialogFragment() {

    lateinit var binding: DialogCommentLayoutBinding
    lateinit var editTextBinding: DialogCommentEditTextBinding
    private var commentPostState: CommentPostState = CommentPostState.Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCommentLayoutBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.commentViewModel = makeViewModel()
        binding.adapter = CommentAdapter(::showCommentMenuDialog)
        binding.recyclerViewComment.setHasFixedSize(true)
        isCancelable = true
        editTextBinding = DialogCommentEditTextBinding.inflate(LayoutInflater.from(context))
        editTextBinding.postComment = ::processPostComment
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val containerLayout =
                dialog?.findViewById<FrameLayout>(com.google.android.material.R.id.container)

            val layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM
            )

            containerLayout?.addView(editTextBinding.root, layoutParams)
            // EditText 레이아웃의 높이 만큼 dialog 레이아웃에 padding 줌
            editTextBinding.root.viewTreeObserver.addOnGlobalLayoutListener(object :
                    ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        editTextBinding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        val height = editTextBinding.root.measuredHeight
                        binding.root.setPadding(0, 0, 0, height)
                    }
                })
        }
        return bottomSheetDialog
    }

    fun show(fragmentManager: FragmentManager, id: Long) {
        showNow(fragmentManager, "CommentDialog")
        binding.commentViewModel?.findComments(id)
        binding.roomId = id
        editTextBinding.roomId = id
    }

    private fun makeViewModel(): CommentViewModel {
        return ViewModelProvider(
            this,
            injectViewModel<CommentViewModel>()
        )[CommentViewModel::class.java]
    }

    private fun processPostComment(roomId: Long, currentComment: String) {
        when (val commentPostState = commentPostState) {
            CommentPostState.Post -> postComment(roomId, currentComment)

            is CommentPostState.Update -> updateComment(
                roomId,
                commentPostState.commentId,
                currentComment
            )
        }
        editTextBinding.currentComment = ""
    }

    private fun postComment(roomId: Long, currentComment: String) {
        binding.commentViewModel?.postComment(
            roomId,
            currentComment
        )
    }

    private fun updateComment(roomId: Long, commentId: Long, currentComment: String) {
        binding.commentViewModel?.updateComment(
            roomId,
            commentId,
            currentComment
        )
        commentPostState = CommentPostState.Post
    }

    private fun showCommentMenuDialog(comment: CommentModel) {
        CommentMenuDialog(
            CommentMenuUiState(update = {
                updateCommentPostState(comment)
            }, delete = {
                    deleteComment(comment)
                })
        ).show(parentFragmentManager, "CommentMenuDialog")
    }

    private fun updateCommentPostState(comment: CommentModel) {
        commentPostState = CommentPostState.Update(comment.id)
        editTextBinding.currentComment = comment.comment
    }

    private fun deleteComment(comment: CommentModel) {
        binding.roomId?.let {
            binding.commentViewModel?.deleteComment(
                it,
                comment.id
            )
        }
        editTextBinding.currentComment = ""
    }
}
