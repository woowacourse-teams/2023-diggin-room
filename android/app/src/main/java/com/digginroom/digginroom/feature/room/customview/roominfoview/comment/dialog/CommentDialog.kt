package com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.digginroom.digginroom.databinding.DialogCommentLayoutBinding
import com.digginroom.digginroom.feature.ResultListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.CommentViewModel
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.adapter.CommentAdapter
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.CommentEventListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.ShowCommentMenuListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class CommentDialog : BottomSheetDialogFragment() {
    lateinit var binding: DialogCommentLayoutBinding
    private lateinit var commentEventListener: CommentEventListener
    private lateinit var postCommentResultListener: ResultListener
    private lateinit var updateCommentResultListener: ResultListener
    private lateinit var deleteCommentResultListener: ResultListener
    private lateinit var commentViewModel: ViewModel
    private lateinit var showCommentMenuListener: ShowCommentMenuListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCommentLayoutBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.commentViewModel = commentViewModel as CommentViewModel?
        binding.adapter = CommentAdapter()
        binding.commentEventListener = commentEventListener
        binding.postCommentResultListener = postCommentResultListener
        binding.updateCommentResultListener = updateCommentResultListener
        binding.deleteCommentResultListener = deleteCommentResultListener
        binding.showMenuListener = showCommentMenuListener
        isCancelable = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            commentEventListener.findComments()
        }
    }

    fun updateCommentEventListener(commentEventListener: CommentEventListener) {
        this.commentEventListener = commentEventListener
    }

    fun updatePostCommentResultListener(resultListener: ResultListener) {
        this.postCommentResultListener = resultListener
    }

    fun updateUpdateCommentResultListener(resultListener: ResultListener) {
        this.updateCommentResultListener = resultListener
    }

    fun updateDeleteCommentResultListener(deleteCommentResultListener: ResultListener) {
        this.deleteCommentResultListener = deleteCommentResultListener
    }

    fun updateViewModel(viewModel: ViewModel) {
        this.commentViewModel = viewModel
    }

    fun updateShowCommentMenuListener(showCommentMenuListener: ShowCommentMenuListener) {
        this.showCommentMenuListener = showCommentMenuListener
    }

    fun updateSelectedCommentId(selectedCommentId: Long) {
        binding.selectedCommentId = selectedCommentId
    }

    fun updateSelectedPosition(selectedPosition: Int) {
        binding.selectedPosition = selectedPosition
    }
}
