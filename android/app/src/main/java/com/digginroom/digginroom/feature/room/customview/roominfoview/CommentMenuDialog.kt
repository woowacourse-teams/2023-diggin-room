package com.digginroom.digginroom.feature.room.customview.roominfoview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.digginroom.digginroom.databinding.DialogCommentMenuLayoutBinding
import com.digginroom.digginroom.model.CommentModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommentMenuDialog : BottomSheetDialogFragment() {
    lateinit var binding: DialogCommentMenuLayoutBinding
    private lateinit var eventListener: CommentMenuEventListener
    lateinit var comment: CommentModel

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
        binding.commentMenuEventListener = eventListener
        binding.comment = comment
    }

    fun setEventListener(eventListener: CommentMenuEventListener) {
        this.eventListener = eventListener
    }

    fun updateComment(comment: CommentModel) {
        this.comment = comment
    }
}
