package com.dygames.roompager.scrollpager

import android.content.Context
import android.view.MotionEvent
import android.widget.HorizontalScrollView
import com.dygames.roompager.PagingOrientation
import com.dygames.roompager.PagingState

class HorizontalScrollPager(
    context: Context
) : ScrollPager, HorizontalScrollView(context) {
    override var pagingState = PagingState.CURRENT
    override var scrollPosition = 0
    override val screenSize = resources.displayMetrics.widthPixels
    override val pagingOrientation: PagingOrientation = PagingOrientation.HORIZONTAL
    override var isScrollable: Boolean = true
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
            if (!isScrollable) return@setOnTouchListener true
            if (event.action != MotionEvent.ACTION_UP) return@setOnTouchListener false
            listener(event)
            dislikeRoom()
            false
        }
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return super.onTouchEvent(ev)
    }

    override fun calculateStartChildPosition(size: Int): Int {
        return ((size * size) / 2) - 1
    }

    override fun calculateEndChildPosition(size: Int): Int {
        return calculateStartChildPosition(size) + size - 1
    }
}
