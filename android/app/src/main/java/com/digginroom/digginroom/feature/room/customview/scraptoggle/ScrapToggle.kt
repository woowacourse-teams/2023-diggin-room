package com.digginroom.digginroom.feature.room.customview.scraptoggle

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView

class ScrapToggle(
    context: Context,
    attributeSet: AttributeSet? = null
) : AppCompatImageView(context, attributeSet) {
    var isScrapped: Boolean = false
    var scrapListener: () -> Unit = {}
    var cancelScrapListener: () -> Unit = {}

    init {
        isClickable = true
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return false
        if (event.action == MotionEvent.ACTION_DOWN) {
            toggle()
        }
        return super.dispatchTouchEvent(event)
    }

    private fun toggle() {
        if (!isScrapped) {
            scrapListener()
        } else {
            cancelScrapListener()
        }
    }
}
