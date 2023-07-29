package com.digginroom.digginroom.views.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.digginroom.digginroom.R

class RoomInfo(context: Context, attributeSet: AttributeSet? = null) :
    ConstraintLayout(context, attributeSet) {
    init {
        val layoutInflater = LayoutInflater.from(context)
        val roomInfoLayout = layoutInflater.inflate(R.layout.room_info, null)
        roomInfoLayout.layoutParams = LinearLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        roomInfoLayout.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
        val displayHeight = resources.displayMetrics.heightPixels
        roomInfoLayout.y = displayHeight.toFloat() - roomInfoLayout.measuredHeight.toFloat()
    }
}