package com.digginroom.digginroom.feature.room.customview.roominfo.comment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.digginroom.digginroom.databinding.DialogCommentMenuLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommentMenuDialog(
    private val uiState: CommentMenuUiState
) : BottomSheetDialogFragment() {
    lateinit var binding: DialogCommentMenuLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCommentMenuLayoutBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        isCancelable = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.uiState = uiState
    }
}
