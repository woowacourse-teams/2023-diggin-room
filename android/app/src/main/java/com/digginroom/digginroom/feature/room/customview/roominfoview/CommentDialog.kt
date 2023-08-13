package com.digginroom.digginroom.feature.room.customview.roominfoview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.digginroom.digginroom.databinding.DialogCommentLayoutBinding
import com.digginroom.digginroom.feature.room.customview.roominfoview.adapter.CommentAdapter
import com.digginroom.digginroom.model.CommentModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommentDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogCommentLayoutBinding
    private lateinit var commentEventListener: CommentEventListener
    val adapter = CommentAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCommentLayoutBinding.inflate(inflater, container, false)
        isCancelable = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        commentEventListener.findComments()
    }

    fun setCommentEventListener(commentEventListener: CommentEventListener) {
        this.commentEventListener = commentEventListener
    }

    fun setUpRecyclerView(comments: List<CommentModel>) {
        adapter.updateItems(comments)
        binding.recyclerViewComment.adapter = adapter
    }
}
