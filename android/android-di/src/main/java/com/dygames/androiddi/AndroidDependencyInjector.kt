package com.dygames.androiddi

import android.content.Context
import com.dygames.di.DependencyInjector.addDependencies
import kotlin.reflect.KType
import kotlin.reflect.typeOf

object AndroidDependencyInjector {
    fun provideContext(context: Context, lifecycle: KType?) {
        addDependencies(typeOf<Context>(), lifecycle, null, context)
    }
}
