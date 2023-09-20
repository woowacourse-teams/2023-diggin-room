package com.digginroom.digginroom.data.di

import android.content.Context
import com.digginroom.digginroom.data.datasource.local.TokenLocalDataSource
import com.digginroom.digginroom.data.datasource.local.TutorialLocalDataSource

class LocalDataSourceProvider(context: Context) {
    val tokenLocalDataSource = TokenLocalDataSource(context)
    val tutorialLocalDataSource = TutorialLocalDataSource(context)
}
