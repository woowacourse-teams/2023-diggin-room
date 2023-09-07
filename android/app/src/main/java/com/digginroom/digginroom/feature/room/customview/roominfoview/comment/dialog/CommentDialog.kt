package com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.digginroom.digginroom.databinding.DialogCommentLayoutBinding
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.CommentViewModel
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.adapter.CommentAdapter
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.CommentEventListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.ShowCommentMenuListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.result.RequestResultListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class CommentDialog : BottomSheetDialogFragment() {
    lateinit var binding: DialogCommentLayoutBinding
    private lateinit var commentEventListener: CommentEventListener
    private lateinit var requestResultListener: RequestResultListener
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
        binding.requestResultListener = requestResultListener
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

    fun updateRequestResultListener(requestResultListener: RequestResultListener) {
        this.requestResultListener = requestResultListener
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
