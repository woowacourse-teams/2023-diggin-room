package com.digginroom.digginroom.feature.room.customview.scraptoggle

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import com.digginroom.digginroom.R

class ScrapToggle(
    context: Context,
    attributeSet: AttributeSet? = null
) : AppCompatImageView(context, attributeSet) {
    private var _isScrapped: Boolean = false
    var scrap: () -> Unit = {}
    var unScrap: () -> Unit = {}

    init {
        isClickable = true
        setImageResource(R.drawable.scrap_icon)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return false
        if (event.action == MotionEvent.ACTION_DOWN) {
            toggle()
        }
        return super.dispatchTouchEvent(event)
    }

    fun interface Function {
        fun invoke()
    }

    fun setOnScrap(event: Function) {
        scrap = {
            event.invoke()
        }
    }

    fun setOnUnScrap(event: Function) {
        unScrap = {
            event.invoke()
        }
    }

    fun setScrapped(isScrapped: Boolean) {
        _isScrapped = isScrapped
        if (isScrapped) {
            setImageResource(R.drawable.scrap_icon)
        } else {
            setImageResource(R.drawable.unscrap_icon)
        }
    }

    private fun toggle() {
        if (!_isScrapped) {
            scrap()
        } else {
            unScrap()
        }
        setScrapped(!_isScrapped)
    }
}
