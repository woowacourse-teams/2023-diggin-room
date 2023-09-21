package com.digginroom.digginroom.data.datasource.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit

class TutorialLocalDataSource(context: Context) {

    private val tutorialPreference =
        context.getSharedPreferences(TUTORIAL_PREFERENCE_NAME, MODE_PRIVATE)

    fun save(tutorialCompleted: Boolean) {
        tutorialPreference.edit {
            putBoolean(KEY_TUTORIAL, tutorialCompleted)
        }
    }

    fun fetch(): Boolean =
        tutorialPreference.getBoolean(KEY_TUTORIAL, true)

    companion object {

        private const val TUTORIAL_PREFERENCE_NAME = "TUTORIAL_PREFERENCE"
        private const val KEY_TUTORIAL = "TUTORIAL"
    }
}
