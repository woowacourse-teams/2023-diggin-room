package com.digginroom.digginroom.views.customView.roompager

import android.view.GestureDetector
import android.view.MotionEvent
import androidx.viewpager2.widget.ViewPager2

class ScrollGestureListener(private val viewPager2: ViewPager2) :
    GestureDetector.SimpleOnGestureListener() {
    override fun onScroll(
        e1: MotionEvent,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        val deltaX = e2.x - e1.x
        val deltaY = e2.y - e1.y

        if (kotlin.math.abs(deltaX) > kotlin.math.abs(deltaY)) {
            viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        } else {
            viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
        }

        return super.onScroll(e1, e2, distanceX, distanceY)
    }
}
