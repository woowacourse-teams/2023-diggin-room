package com.digginroom.digginroom.views.customview.roompager

import android.content.Context
import android.view.MotionEvent
import android.widget.HorizontalScrollView

class HorizontalScrollPager(
    context: Context
) : ScrollPager, HorizontalScrollView(context) {
    override var pagingState = PagingState.CURRENT
    override var scrollPosition = 0
    override val screenSize = resources.displayMetrics.widthPixels

    override fun smoothScrollTo(position: Int) {
        smoothScrollTo(position, 0)
    }

    override fun scrollBy(position: Int) {
        scrollBy(position, 0)
    }

    override fun scrollTo(position: Int) {
        scrollTo(position, 0)
    }

    override fun post(action: () -> Unit) {
        post {
            action()
        }
    }

    override fun setOnScrollChangeListener(listener: (Int) -> Unit) {
        setOnScrollChangeListener { _, scrollX, _, _, _ ->
            listener(scrollX)
        }
    }

    override fun setOnTouchListener(listener: (MotionEvent) -> Unit) {
        setOnTouchListener { _, event ->
            listener(event)
            false
        }
    }

    override fun calculateStartChildPosition(index: Int, size: Int): Int {
        return index * size
    }

    override fun calculateEndChildPosition(index: Int, size: Int): Int {
        return index * size + (size - 1)
    }


}
