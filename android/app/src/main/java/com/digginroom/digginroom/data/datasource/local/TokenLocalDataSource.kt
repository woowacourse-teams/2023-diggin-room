package com.digginroom.digginroom.data.datasource.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit

class TokenLocalDataSource(context: Context) {

    private val tokenPreference =
        context.getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE)

    fun save(token: String) {
        tokenPreference.edit {
            putString(KEY_TOKEN, token)
        }
    }

    fun fetch(): String =
        tokenPreference.getString(KEY_TOKEN, "") ?: ""

    companion object {

        private const val SHARED_PREFERENCE_NAME = "TOKEN_PREFERENCE"
        private const val KEY_TOKEN = "TOKEN"
    }
}
