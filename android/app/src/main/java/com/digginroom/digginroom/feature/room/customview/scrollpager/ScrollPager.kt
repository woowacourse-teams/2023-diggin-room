package com.digginroom.digginroom.feature.room.customview.scrollpager

import android.view.MotionEvent
import com.digginroom.digginroom.feature.room.customview.roompager.PagingState

interface ScrollPager {
    var pagingState: PagingState
    var scrollPosition: Int
    val screenSize: Int
    fun smoothScrollTo(position: Int)
    fun scrollBy(position: Int)
    fun scrollTo(position: Int)
    fun post(action: () -> Unit)
    fun setOnScrollChangeListener(listener: (Int) -> Unit)
    fun setOnTouchListener(listener: (MotionEvent) -> Unit)
    fun calculateStartChildPosition(size: Int): Int
    fun calculateEndChildPosition(size: Int): Int
}
