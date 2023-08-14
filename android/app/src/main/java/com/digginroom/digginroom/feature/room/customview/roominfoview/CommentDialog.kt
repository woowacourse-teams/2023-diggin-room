package com.digginroom.digginroom.feature.room.customview.roominfoview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.digginroom.digginroom.databinding.DialogCommentLayoutBinding
import com.digginroom.digginroom.feature.ResultListener
import com.digginroom.digginroom.feature.room.CommentViewModel
import com.digginroom.digginroom.feature.room.customview.roominfoview.adapter.CommentAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class CommentDialog : BottomSheetDialogFragment() {
    lateinit var binding: DialogCommentLayoutBinding
    private lateinit var commentEventListener: CommentEventListener
    private lateinit var postCommentResultListener: ResultListener
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

    fun setCommentEventListener(commentEventListener: CommentEventListener) {
        this.commentEventListener = commentEventListener
    }

    fun setPostCommentResultListener(resultListener: ResultListener) {
        this.postCommentResultListener = resultListener
    }

    fun setViewModel(viewModel: ViewModel) {
        this.commentViewModel = viewModel
    }

    fun setShowCommentMenuListener(showCommentMenuListener: ShowCommentMenuListener) {
        this.showCommentMenuListener = showCommentMenuListener
    }
}
