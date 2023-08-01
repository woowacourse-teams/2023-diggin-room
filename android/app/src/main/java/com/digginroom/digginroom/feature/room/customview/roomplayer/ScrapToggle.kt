package com.digginroom.digginroom.views.customview.roomview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import com.digginroom.digginroom.R

class ScrapToggle(
    context: Context,
    attributeSet: AttributeSet? = null,
) :
    AppCompatImageView(context, attributeSet) {
    var isScrapped: Boolean = false
        set(value) {
            if (value) {
                setImageResource(R.drawable.scrap_icon)
            } else {
                setImageResource(R.drawable.unscrap_icon)
            }
            field = value
        }
    var onScrapClickListener: () -> Unit = {}

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
        isScrapped = !isScrapped
        onScrapClickListener()
    }
}
