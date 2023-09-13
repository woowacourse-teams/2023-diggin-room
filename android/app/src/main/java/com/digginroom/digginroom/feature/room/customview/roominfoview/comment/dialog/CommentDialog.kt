package com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.data.di.ViewModelFactory
import com.digginroom.digginroom.databinding.DialogCommentLayoutBinding
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.CommentViewModel
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.adapter.CommentAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommentDialog : BottomSheetDialogFragment() {

    private val commentViewModel: CommentViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory.getInstance(requireContext()).commentViewModelFactory
        )[CommentViewModel::class.java]
    }
    lateinit var binding: DialogCommentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCommentLayoutBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.adapter = CommentAdapter()
        binding.commentViewModel = commentViewModel
        isCancelable = true
        return binding.root
    }

    fun show(fragmentManager: FragmentManager, id: Long) {
        commentViewModel.findComments(id)
        show(fragmentManager, "")
    }
}
