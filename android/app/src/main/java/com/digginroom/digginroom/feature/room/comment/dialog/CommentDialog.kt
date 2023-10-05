package com.digginroom.digginroom.feature.room.comment.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.DialogCommentLayoutBinding
import com.digginroom.digginroom.databinding.DialogCommentStickyItemLayoutBinding
import com.digginroom.digginroom.feature.room.comment.CommentViewModel
import com.digginroom.digginroom.feature.room.comment.adapter.CommentAdapter
import com.digginroom.digginroom.feature.room.comment.uistate.CommentMenuUiState
import com.digginroom.digginroom.feature.room.comment.uistate.state.CommentPostState
import com.digginroom.digginroom.model.CommentModel
import com.dygames.androiddi.ViewModelDependencyInjector.injectViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommentDialog : BottomSheetDialogFragment() {

    lateinit var binding: DialogCommentLayoutBinding
    lateinit var stickyItemLayoutBinding: DialogCommentStickyItemLayoutBinding
    private var commentPostState: CommentPostState = CommentPostState.Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initDialogBinding()
        initEditTextBinding()
        isCancelable = true
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val bottomSheetDialogParent =
                bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            bottomSheetDialogParent.makeBottomSheetDialogParentFullSize()
            val containerLayout =
                bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.container)

            initBottomSheetBehavior(bottomSheetDialogParent, containerLayout as ViewGroup)

            containerLayout.addStickyView(stickyItemLayoutBinding.root)
            binding.root.addPaddingAsStickyViewHeight(stickyItemLayoutBinding.root)
        }
        return bottomSheetDialog
    }

    private fun ViewGroup.makeBottomSheetDialogParentFullSize() {
        this.layoutParams.height = CoordinatorLayout.LayoutParams.MATCH_PARENT
    }

    private fun initBottomSheetBehavior(view: View, viewGroup: ViewGroup) {
        val bottomSheetBehavior = BottomSheetBehavior.from(view)
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                bottomSheetBehavior.removeBottomSheetCallback(this)
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> viewGroup.removeAllViews()
                    else -> {}
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    private fun initDialogBinding() {
        binding = DialogCommentLayoutBinding.inflate(LayoutInflater.from(context))
        binding.lifecycleOwner = this
        binding.commentViewModel = makeViewModel()
        binding.adapter = CommentAdapter(::showCommentMenuDialog)
    }

    private fun initEditTextBinding() {
        stickyItemLayoutBinding =
            DialogCommentStickyItemLayoutBinding.inflate(LayoutInflater.from(context))
        stickyItemLayoutBinding.postComment = ::processPostComment
    }

    private fun ViewGroup.addStickyView(view: View) {
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.BOTTOM
        )

        this.addView(view, layoutParams)
    }

    private fun View.addPaddingAsStickyViewHeight(stickyView: View) {
        stickyView.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    stickyView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    this@addPaddingAsStickyViewHeight.setPadding(0, 0, 0, stickyView.measuredHeight)
                }
            })
    }

    fun show(fragmentManager: FragmentManager, id: Long) {
        showNow(fragmentManager, "CommentDialog")
        binding.commentViewModel?.findComments(id)
        binding.roomId = id
        stickyItemLayoutBinding.roomId = id
    }

    private fun makeViewModel(): CommentViewModel {
        return ViewModelProvider(
            this,
            injectViewModel<CommentViewModel>()
        )[CommentViewModel::class.java]
    }

    private fun processPostComment(roomId: Long, currentComment: String) {
        when (val commentPostState = commentPostState) {
            CommentPostState.Post -> postComment(roomId, currentComment)

            is CommentPostState.Update -> updateComment(
                roomId,
                commentPostState.commentId,
                currentComment
            )
        }
        stickyItemLayoutBinding.currentComment = ""
    }

    private fun postComment(roomId: Long, currentComment: String) {
        binding.commentViewModel?.postComment(
            roomId,
            currentComment
        )
    }

    private fun updateComment(roomId: Long, commentId: Long, currentComment: String) {
        binding.commentViewModel?.updateComment(
            roomId,
            commentId,
            currentComment
        )
        commentPostState = CommentPostState.Post
    }

    private fun showCommentMenuDialog(comment: CommentModel) {
        CommentMenuDialog(
            CommentMenuUiState(update = {
                updateCommentPostState(comment)
            }, delete = {
                    deleteComment(comment)
                })
        ).show(parentFragmentManager, "CommentMenuDialog")
    }

    private fun updateCommentPostState(comment: CommentModel) {
        commentPostState = CommentPostState.Update(comment.id)
        stickyItemLayoutBinding.currentComment = comment.comment
    }

    private fun deleteComment(comment: CommentModel) {
        binding.roomId?.let {
            binding.commentViewModel?.deleteComment(
                it,
                comment.id
            )
        }
        stickyItemLayoutBinding.currentComment = ""
    }
}
