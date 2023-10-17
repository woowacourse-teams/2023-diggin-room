package com.dygames.roompager.util

import android.content.res.Resources
import android.os.Build
import android.view.WindowManager
import androidx.core.view.WindowInsetsCompat

fun getScreenHeight(windowManager: WindowManager): Int {
    val realHeight = getRealHeight(windowManager)

    return when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
            realHeight - windowManager
                .currentWindowMetrics
                .windowInsets
                .getInsets(WindowInsetsCompat.Type.navigationBars())
                .bottom
        }

        else -> realHeight - getNavHeight()
    }
}

private fun getRealHeight(windowManager: WindowManager): Int {
    return if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
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
    with(Resources.getSystem()) {
        val navHeight = getIdentifier(
            "navigation_bar_height",
            "dimen",
            "android"
        )

        return if (navHeight > 0) {
            getDimensionPixelSize(navHeight)
        } else {
            0
        }
    }
}
