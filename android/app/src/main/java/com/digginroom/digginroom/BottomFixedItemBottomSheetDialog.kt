package com.digginroom.digginroom

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BottomFixedItemBottomSheetDialog : BottomSheetDialogFragment() {
    abstract val dialogView: View
    abstract val bottomFixedItemView: View

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
                findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                    ?: return@setOnShowListener
            bottomSheetDialogParentContainer =
                findViewById<FrameLayout>(com.google.android.material.R.id.container)
                    ?: return@setOnShowListener
            bottomSheetDialogParent.makeFullSize()
            bottomSheetDialogParentContainer.addStickyView(bottomFixedItemView)
            dialogView.addPaddingAsStickyViewHeight(bottomFixedItemView)
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        (bottomSheetDialogParentContainer as ViewGroup).removeView(bottomFixedItemView)
    }

    private fun View.makeFullSize() {
        this.layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT
        requestLayout()
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
        stickyView.post {
            this@addPaddingAsStickyViewHeight.setPadding(0, 0, 0, stickyView.measuredHeight)
        }
    }
}
