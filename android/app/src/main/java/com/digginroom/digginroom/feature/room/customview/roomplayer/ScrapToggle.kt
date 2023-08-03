package com.digginroom.digginroom.feature.room.customview.roomplayer

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import com.digginroom.digginroom.R
import com.digginroom.digginroom.feature.room.ScrapListener

class ScrapToggle(
    context: Context,
    attributeSet: AttributeSet? = null
) :
    AppCompatImageView(context, attributeSet) {
    var isScrapped: Boolean = false

    var scrapListener: ScrapListener = object : ScrapListener {
        override fun scrap(roomId: Long) {}
        override fun cancelScrap(roomId: Long) {}
    }

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
            scrapListener.scrap(0)
            setImageResource(R.drawable.scrap_icon)
        } else {
            scrapListener.cancelScrap(0)
            setImageResource(R.drawable.unscrap_icon)
        }
        isScrapped = !isScrapped
    }
}
