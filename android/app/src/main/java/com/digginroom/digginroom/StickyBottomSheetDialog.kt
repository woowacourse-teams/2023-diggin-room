package com.digginroom.digginroom

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class StickyBottomSheetDialog : BottomSheetDialogFragment() {
    abstract val dialogView: View
    abstract val stickyView: View

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var bottomSheetDialogParent: FrameLayout
    private lateinit var bottomSheetDialogParentContainer: FrameLayout

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.initShowListener()
        return bottomSheetDialog
    }

    private fun BottomSheetDialog.initShowListener() {
        setOnShowListener {
            bottomSheetDialogParent =
                bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            bottomSheetDialogParentContainer =
                bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.container) as FrameLayout
            bottomSheetDialogParent.makeFullSize()
            bottomSheetDialogParentContainer.addStickyView(stickyView)
            dialogView.addPaddingAsStickyViewHeight(stickyView)
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        (bottomSheetDialogParentContainer as ViewGroup).removeView(stickyView)
        super.onCancel(dialog)
    }

    private fun ViewGroup.makeFullSize() {
        this.layoutParams.height = CoordinatorLayout.LayoutParams.MATCH_PARENT
    }

    private fun ViewGroup.addStickyView(view: View) {
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.BOTTOM
        )
        addView(view, layoutParams)
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
}
