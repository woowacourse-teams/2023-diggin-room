package com.dygames.roompager.scrollpager

import android.content.Context
import android.view.MotionEvent
import android.widget.ScrollView
import com.dygames.roompager.PagingOrientation
import com.dygames.roompager.PagingState

class VerticalScrollPager(context: Context) : ScrollPager, ScrollView(context) {
    override var pagingState = PagingState.CURRENT
    override var scrollPosition = 0
    override val screenSize = resources.displayMetrics.heightPixels
    override val pagingOrientation: PagingOrientation = PagingOrientation.VERTICAL
    override var isScrollable: Boolean = true

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
        post(Runnable(action))
    }

    override fun setOnScrollChangeListener(listener: (Int) -> Unit) {
        setOnScrollChangeListener { _, _, scrollY, _, _ ->
            listener(scrollY)
        }
    }

    override fun setOnTouchListener(listener: (MotionEvent) -> Unit) {
        setOnTouchListener { _, event ->
            if (!isScrollable) return@setOnTouchListener true
            if (event.action != MotionEvent.ACTION_UP) return@setOnTouchListener false
            listener(event)
            false
        }
    }

    override fun calculateStartChildPosition(size: Int): Int {
        return size / 2
    }

    override fun calculateEndChildPosition(size: Int): Int {
        return (size * size) - calculateStartChildPosition(size) - 1
    }
}
