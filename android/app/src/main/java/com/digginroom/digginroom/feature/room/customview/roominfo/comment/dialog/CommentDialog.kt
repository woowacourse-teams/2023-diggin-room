package com.digginroom.digginroom.feature.room.customview.roominfo.comment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.data.di.ViewModelFactory
import com.digginroom.digginroom.databinding.DialogCommentLayoutBinding
import com.digginroom.digginroom.feature.room.customview.roominfo.comment.CommentPostState
import com.digginroom.digginroom.feature.room.customview.roominfo.comment.CommentViewModel
import com.digginroom.digginroom.feature.room.customview.roominfo.comment.adapter.CommentAdapter
import com.digginroom.digginroom.feature.room.customview.roominfo.comment.dialog.listener.CommentMenuEvent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommentDialog : BottomSheetDialogFragment() {

    lateinit var binding: DialogCommentLayoutBinding
    var commentPostState: CommentPostState = CommentPostState.Post

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
        binding.postComment = {
            when (commentPostState) {
                CommentPostState.Post -> binding.commentViewModel?.postComment(
                    binding.roomId!!,
                    binding.currentComment!!
                )

                is CommentPostState.Update -> binding.commentViewModel?.updateComment(
                    binding.roomId!!,
                    (commentPostState as CommentPostState.Update).commentId,
                    binding.currentComment!!
                )
            }
        }
        binding.adapter = CommentAdapter { comment ->
            CommentMenuDialog(object : CommentMenuEvent {
                override fun update() {
                    commentPostState = CommentPostState.Update(comment.id)
                }

                override fun delete() {
                    DeleteCommentAlertDialog {
                        binding.roomId?.let {
                            binding.commentViewModel?.deleteComment(
                                it,
                                comment.id
                            )
                        }
                    }.show(parentFragmentManager, "DeleteCommentAlertDialog")
                }
            }).show(parentFragmentManager, "CommentMenuDialog")
        }
        isCancelable = true
        return binding.root
    }

    fun show(fragmentManager: FragmentManager, id: Long) {
        showNow(fragmentManager, "CommentDialog")
        binding.commentViewModel?.findComments(id)
        binding.roomId = id
    }
}
