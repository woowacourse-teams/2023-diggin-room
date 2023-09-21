package com.dygames.androiddi.lifecycle

import android.app.Application
import com.dygames.androiddi.AndroidDependencyInjector
import com.dygames.di.DependencyInjector
import kotlin.reflect.KType

abstract class LifecycleWatcherApplication(val lifecycle: KType) : Application() {
    private val lifecycleWatcher: LifecycleWatcher = object : LifecycleWatcher(lifecycle) {
        override fun createDependencies() {
            super.createDependencies()
            AndroidDependencyInjector.provideContext(this@LifecycleWatcherApplication, lifecycle)
        }
    }

    inline fun <reified T : Any> inject(): T {
        return DependencyInjector.inject<T>(lifecycle)
    }

    override fun onCreate() {
        lifecycleWatcher.createDependencies()
        super.onCreate()
    }

    override fun onTerminate() {
        super.onTerminate()
        lifecycleWatcher.destroyDependencies()
    }
}
