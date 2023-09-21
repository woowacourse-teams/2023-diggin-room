package com.digginroom.digginroom.feature.room.comment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.databinding.DialogCommentLayoutBinding
import com.digginroom.digginroom.feature.room.comment.CommentViewModel
import com.digginroom.digginroom.feature.room.comment.adapter.CommentAdapter
import com.digginroom.digginroom.feature.room.comment.uistate.CommentMenuUiState
import com.digginroom.digginroom.feature.room.comment.uistate.state.CommentPostState
import com.digginroom.digginroom.model.CommentModel
import com.dygames.androiddi.ViewModelDependencyInjector.injectViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommentDialog : BottomSheetDialogFragment() {

    lateinit var binding: DialogCommentLayoutBinding
    private var commentPostState: CommentPostState = CommentPostState.Post

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DialogCommentLayoutBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.commentViewModel = makeViewModel()
        binding.postComment = ::processPostComment
        binding.adapter = CommentAdapter(::showCommentMenuDialog)
        isCancelable = true
        return binding.root
    }

    fun show(fragmentManager: FragmentManager, id: Long) {
        showNow(fragmentManager, "CommentDialog")
        binding.commentViewModel?.findComments(id)
        binding.roomId = id
    }

    private fun makeViewModel(): CommentViewModel {
        return ViewModelProvider(
            this, injectViewModel<CommentViewModel>()
        )[CommentViewModel::class.java]
    }

    private fun processPostComment(roomId: Long, currentComment: String) {
        when (val commentPostState = commentPostState) {
            CommentPostState.Post -> postComment(roomId, currentComment)

            is CommentPostState.Update -> updateComment(
                roomId, commentPostState.commentId, currentComment
            )
        }
        binding.currentComment = ""
    }

    private fun postComment(roomId: Long, currentComment: String) {
        binding.commentViewModel?.postComment(
            roomId, currentComment
        )
    }

    private fun updateComment(roomId: Long, commentId: Long, currentComment: String) {
        binding.commentViewModel?.updateComment(
            roomId, commentId, currentComment
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
        binding.currentComment = comment.comment
    }

    private fun deleteComment(comment: CommentModel) {
        binding.roomId?.let {
            binding.commentViewModel?.deleteComment(
                it, comment.id
            )
        }
        binding.currentComment = ""
    }
}
