package com.digginroom.digginroom.views.customView.roompager

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.digginroom.digginroom.views.model.RoomModel

class RoomPager(
    context: Context,
    attributeSet: AttributeSet,
) : LinearLayout(context, attributeSet) {
    private val viewPager2: ViewPager2 = ViewPager2(context, attributeSet)
    private val gestureDetector = ScrollGestureListener(viewPager2)
    var rooms: List<RoomModel> = emptyList()

    var currentEvent: MotionEvent = MotionEvent.obtain(0,0,0,0.0f,0.0f,0)

    init {
        viewPager2.adapter = RoomPagerAdapter(rooms)
        addView(viewPager2)

        viewPager2.getChildAt(0).setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_DOWN)
                gestureDetector.onDown(event)
            else if (event.action == MotionEvent.ACTION_MOVE)
                gestureDetector.onScroll(currentEvent, event, 0.0f, 0.0f)
            currentEvent = event
            performClick()
        }
    }

    fun updateData(rooms: List<RoomModel>) {
        this.rooms = rooms
        (viewPager2.adapter as RoomPagerAdapter).setData(rooms)
    }
}
