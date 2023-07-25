package com.digginroom.digginroom.views.customview.roompager

import android.content.Context
import android.view.MotionEvent
import android.widget.ScrollView

class VerticalScrollPager(context: Context) : ScrollPager, ScrollView(context) {
    override var pagingState = PagingState.CURRENT
    override var scrollPosition = 0
    override val screenSize = resources.displayMetrics.heightPixels

    override fun smoothScrollTo(position: Int) {
        smoothScrollTo(0, position)
    }

    override fun scrollBy(position: Int) {
        scrollBy(0, position)
    }

    override fun scrollTo(position: Int) {
        scrollTo(0, position)
    }

    override fun post(action: () -> Unit) {
        post {
            action()
        }
    }

    override fun setOnScrollChangeListener(listener: (Int) -> Unit) {
        setOnScrollChangeListener { _, _, scrollY, _, _ ->
            listener(scrollY)
        }
    }

    override fun setOnTouchListener(listener: (MotionEvent) -> Unit) {
        setOnTouchListener { _, event ->
            listener(event)
            false
        }
    }

    override fun calculateStartChildPosition(index: Int, size: Int): Int {
        return 0
    }

    override fun calculateEndChildPosition(index: Int, size: Int): Int {
        return (size * size) - 1
    }
}
