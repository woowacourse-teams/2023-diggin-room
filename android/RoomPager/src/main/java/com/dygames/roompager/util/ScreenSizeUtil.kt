package com.dygames.roompager.util

import android.content.res.Resources
import kotlin.math.roundToInt

fun getNotchHeight(): Int {
    val statusBarHeight = getStatusBarHeight()

    if (statusBarHeight > convertDpToPixel(24)) return statusBarHeight
    return 0
}

private fun getStatusBarHeight(): Int {
    var result = 0
    val resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = Resources.getSystem().getDimensionPixelSize(resourceId)
    }
    return result
}

private fun convertDpToPixel(dp: Int): Int {
    val metrics = Resources.getSystem().displayMetrics
    val px = dp * (metrics.densityDpi / 160f)
    return px.roundToInt()
}
