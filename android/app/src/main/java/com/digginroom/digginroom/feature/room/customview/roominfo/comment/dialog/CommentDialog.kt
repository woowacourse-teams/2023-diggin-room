package com.digginroom.digginroom.feature.room.customview.roominfo.comment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.data.di.ViewModelFactory
import com.digginroom.digginroom.databinding.DialogCommentLayoutBinding
import com.digginroom.digginroom.feature.room.customview.roominfo.comment.CommentViewModel
import com.digginroom.digginroom.feature.room.customview.roominfo.comment.adapter.CommentAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommentDialog : BottomSheetDialogFragment() {

    lateinit var binding: DialogCommentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCommentLayoutBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.commentViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(inflater.context).commentViewModelFactory
        )[CommentViewModel::class.java]
        binding.adapter = CommentAdapter()
        isCancelable = true
        return binding.root
    }

    fun show(fragmentManager: FragmentManager, id: Long) {
        showNow(fragmentManager, "")
        binding.commentViewModel?.findComments(id)
    }
}
