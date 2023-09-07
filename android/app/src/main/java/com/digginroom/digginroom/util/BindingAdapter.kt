package com.digginroom.digginroom.util

import android.content.res.Resources
import android.graphics.Rect
import android.util.TypedValue
import android.view.TouchDelegate
import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("app:onSingleClick")
    fun setOnSingleClickListener(view: View, onClicked: () -> Unit) {
        var previousClickedTime = 0L
        view.setOnClickListener {
            val clickedTime = System.currentTimeMillis()
            if (clickedTime - previousClickedTime >= 500L) {
                onClicked()
                previousClickedTime = clickedTime
            }
        }
    }

    @JvmStatic
    @BindingAdapter("app:increaseTouchRect")
    fun increaseTouchRect(view: View, value: Int) {
        val parent = view.parent as View
        val rect = Rect()
        parent.post {
            view.getHitRect(rect)
            val px = value.toPx
            rect.top -= px
            rect.left -= px
            rect.bottom += px
            rect.right += px
            parent.touchDelegate = TouchDelegate(rect, view)
        }
    }

    private val Number.toPx
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
}
