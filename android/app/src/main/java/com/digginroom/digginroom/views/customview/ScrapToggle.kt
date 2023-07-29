package com.digginroom.digginroom.views.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.digginroom.digginroom.R

class ScrapToggle(context: Context, attributeSet: AttributeSet? = null) :
    AppCompatImageView(context, attributeSet) {
    private var isScrapped: Boolean = false
    private val iconSize = resources.getDimensionPixelSize(R.dimen.scrap_icon_size)
    private val customLayoutParams = ViewGroup.LayoutParams(iconSize, iconSize)

    init {
        isClickable = true
        layoutParams = customLayoutParams
        setImageResource(R.drawable.unscrap_icon)
        setPosition()
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return false
        if (event.action == MotionEvent.ACTION_DOWN) {
            toggle()
        }
        return super.dispatchTouchEvent(event)
    }

    private fun toggle() {
        isScrapped = if (isScrapped) {
            setImageResource(R.drawable.unscrap_icon)
            false
        } else {
            setImageResource(R.drawable.scrap_icon)
            true
        }
    }

    private fun setPosition() {
        val displayWidth = resources.displayMetrics.widthPixels
        val displayHeight = resources.displayMetrics.heightPixels
        val iconSize = resources.getDimensionPixelSize(R.dimen.scrap_icon_size)
        x =
            (displayWidth - resources.getDimensionPixelSize(R.dimen.scrap_right_margin) - iconSize).toFloat()
        y =
            (displayHeight - resources.getDimensionPixelSize(R.dimen.scrap_bottom_margin) - iconSize).toFloat()
    }
}