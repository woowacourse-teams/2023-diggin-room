package com.digginroom.digginroom.data.di

import android.content.Context
import com.digginroom.digginroom.data.datasource.local.TokenLocalDataSource

class LocalDataSourceProvider(context: Context) {
    val tokenLocalDataSource = TokenLocalDataSource(context)
}
