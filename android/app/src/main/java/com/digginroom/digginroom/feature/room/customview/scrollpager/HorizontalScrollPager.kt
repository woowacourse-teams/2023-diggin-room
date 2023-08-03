package com.digginroom.digginroom.feature.room.customview.scrollpager

import android.content.Context
import android.view.MotionEvent
import android.widget.HorizontalScrollView
import com.digginroom.digginroom.feature.room.customview.roompager.PagingState

class HorizontalScrollPager(
    context: Context
) : ScrollPager, HorizontalScrollView(context) {
    override var pagingState = PagingState.CURRENT
    override var scrollPosition = 0
    override val screenSize = resources.displayMetrics.widthPixels
    var dislikeRoom: () -> Unit = { }

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
        post(Runnable(action))
    }

    override fun setOnScrollChangeListener(listener: (Int) -> Unit) {
        setOnScrollChangeListener { _, scrollX, _, _, _ ->
            listener(scrollX)
        }
    }

    override fun setOnTouchListener(listener: (MotionEvent) -> Unit) {
        setOnTouchListener { _, event ->
            listener(event)
            dislikeRoom()
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
