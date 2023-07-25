package com.digginroom.digginroom.views.customview.roompager

import android.view.MotionEvent

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
    fun calculateStartChildPosition(index: Int, size: Int): Int
    fun calculateEndChildPosition(index: Int, size: Int): Int
}
