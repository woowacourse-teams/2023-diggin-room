package com.digginroom.digginroom.data.datasource.local

import android.content.Context
import androidx.annotation.Keep
import androidx.core.content.edit
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class ExtractionStateDataSource @Keep constructor(
    context: Context
) {

    private val extractionPreference =
        context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun save(expectedTime: Long) {
        runBlocking {
            extractionPreference.edit {
                putBoolean(KEY_AVAILABLE, false)
            }
            delay(expectedTime * UNIT)
            extractionPreference.edit {
                putBoolean(KEY_AVAILABLE, true)
            }
        }
    }

    fun fetchIsAvailable(): Boolean =
        extractionPreference.getBoolean(KEY_AVAILABLE, true)

    companion object {

        private const val SHARED_PREFERENCE_NAME = "EXTRACTION_PREFERENCE"
        private const val KEY_AVAILABLE = "AVAILABLE"
        private const val UNIT = 1300
    }
}
