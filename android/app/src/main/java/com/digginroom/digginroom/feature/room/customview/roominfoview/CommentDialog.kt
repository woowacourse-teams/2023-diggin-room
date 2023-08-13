package com.digginroom.digginroom.feature.room.customview.roominfoview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.digginroom.digginroom.databinding.DialogCommentLayoutBinding
import com.digginroom.digginroom.feature.room.CommentViewModel
import com.digginroom.digginroom.feature.room.customview.roominfoview.adapter.CommentAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class CommentDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogCommentLayoutBinding
    private lateinit var commentEventListener: CommentEventListener
    private lateinit var commentViewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCommentLayoutBinding.inflate(inflater, container, false)
        binding.commentViewModel = commentViewModel as CommentViewModel?
        binding.adapter = CommentAdapter()
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

    fun setViewModel(viewModel: ViewModel) {
        this.commentViewModel = viewModel
    }
}
