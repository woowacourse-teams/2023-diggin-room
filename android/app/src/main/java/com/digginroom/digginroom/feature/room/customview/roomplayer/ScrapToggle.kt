package com.digginroom.digginroom.feature.room.customview.roomplayer

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import com.digginroom.digginroom.R

class ScrapToggle(
    context: Context,
    attributeSet: AttributeSet? = null
) :
    AppCompatImageView(context, attributeSet) {
    var isScrapped: Boolean = false
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
        if (isScrapped) {
            setImageResource(R.drawable.scrap_icon)
        } else {
            setImageResource(R.drawable.unscrap_icon)
        }
        isScrapped = !isScrapped
        onScrapClickListener()
    }
}
