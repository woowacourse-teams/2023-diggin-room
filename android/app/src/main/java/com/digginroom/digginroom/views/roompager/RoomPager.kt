package com.digginroom.digginroom.views.roompager

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import com.digginroom.model.TempUiModel

class RoomPager @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
) : FrameLayout(context, attributeSet) {
    val viewPager2: ViewPager2 = ViewPager2(context, attributeSet)
    var data: List<TempUiModel> = emptyList()

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
}
