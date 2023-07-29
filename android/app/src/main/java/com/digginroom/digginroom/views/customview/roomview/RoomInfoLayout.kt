package com.digginroom.digginroom.views.customview.roomview

import android.content.Context
import android.graphics.Color
import android.widget.LinearLayout
import android.widget.TextView
import com.digginroom.digginroom.R
import com.digginroom.digginroom.views.customview.ScrapToggle

class RoomInfoLayout(context: Context):LinearLayout(context) {
    init {
        val scrapToggle = ScrapToggle(context)
        addView(scrapToggle)
        val infoText = TextView(context)
        infoText.text = "hello~~"
        infoText.setTextColor(Color.WHITE)
        val displayWidth = resources.displayMetrics.widthPixels
        val displayHeight = resources.displayMetrics.heightPixels
        infoText.textSize = 24F
        infoText.x =
            (displayWidth - resources.getDimensionPixelSize(R.dimen.scrap_right_margin)-infoText.width).toFloat()
        infoText.y =
            (displayHeight - resources.getDimensionPixelSize(R.dimen.scrap_bottom_margin)-infoText.height).toFloat()
        addView(infoText)
    }
}