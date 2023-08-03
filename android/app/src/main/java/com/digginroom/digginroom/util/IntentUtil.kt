package com.digginroom.digginroom.feature

import android.content.Intent
import android.os.Build
import java.io.Serializable

@Suppress("DEPRECATION")
inline fun <reified T : Serializable> Intent.getSerializableCompat(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializableExtra(key, T::class.java)
    } else {
        getSerializableExtra(key) as? T
    }
}
