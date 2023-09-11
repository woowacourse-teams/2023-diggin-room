package com.dygames.roompager.scrollpager

import android.view.MotionEvent
import com.dygames.roompager.PagingOrientation
import com.dygames.roompager.PagingState

interface ScrollPager {
    var pagingState: PagingState
    var scrollPosition: Int
    val screenSize: Int
    val pagingOrientation: PagingOrientation
    var isScrollable: Boolean
    fun smoothScrollTo(position: Int)
    fun scrollBy(position: Int)
    fun scrollTo(position: Int)
    fun post(action: () -> Unit)
    fun setOnScrollChangeListener(listener: (Int) -> Unit)
    fun setOnTouchListener(listener: (MotionEvent) -> Unit)
    fun calculateStartChildPosition(size: Int): Int
    fun calculateEndChildPosition(size: Int): Int
}
