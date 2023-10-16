package com.dygames.roompager.util

import android.content.res.Resources
import android.os.Build
import android.view.WindowManager

fun getScreenHeight(windowManager: WindowManager): Int {
    val realHeight = getRealHeight(windowManager)

    return realHeight - getNavHeight()
}

private fun getRealHeight(windowManager: WindowManager): Int {
    return if (Build.VERSION.SDK_INT <= 30) {
        val displayMetrics = Resources
            .getSystem()
            .displayMetrics
            .apply {
                windowManager.defaultDisplay.getRealMetrics(this)
            }

        displayMetrics.heightPixels
    } else {
        windowManager
            .currentWindowMetrics
            .bounds
            .height()
    }
}

private fun getNavHeight(): Int {
    val navHeight = Resources.getSystem().getIdentifier("navigation_bar_height", "dimen", "android")

    return if (navHeight > 0) {
        Resources.getSystem().getDimensionPixelSize(navHeight)
    } else {
        0
    }
}
