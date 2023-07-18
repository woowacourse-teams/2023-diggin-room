package com.digginroom.digginroom.views.customView.roompager

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import com.digginroom.digginroom.views.model.RoomModel

class RoomPager @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
) : FrameLayout(context, attributeSet) {
    val viewPager2: ViewPager2 = ViewPager2(context, attributeSet)
    var data: List<RoomModel> = emptyList()

    init {
        addView(viewPager2)
        val gestureDetector = GestureDetector(context, ScrollGestureListener(viewPager2))
        viewPager2.apply {
            adapter = PagerAdapter(data)
            getChildAt(0).setOnTouchListener { _, event ->
                gestureDetector.onTouchEvent(event)
                performClick()
            }
            registerOnPageChangeCallback(PageChangeListener())
        }
    }

    fun updateData() {
        viewPager2.adapter = PagerAdapter(data)
    }
}
