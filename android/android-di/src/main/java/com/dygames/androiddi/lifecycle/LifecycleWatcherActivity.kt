package com.dygames.androiddi.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dygames.androiddi.AndroidDependencyInjector
import com.dygames.di.DependencyInjector
import kotlin.reflect.KType

abstract class LifecycleWatcherActivity(val lifecycle: KType) : AppCompatActivity() {
    private val lifecycleWatcher: LifecycleWatcher = object : LifecycleWatcher(lifecycle) {
        override fun createDependencies() {
            super.createDependencies()
            AndroidDependencyInjector.provideContext(this@LifecycleWatcherActivity, lifecycle)
        }
    }

    inline fun <reified T : Any> inject(): T {
        return DependencyInjector.inject<T>(lifecycle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleWatcher.createDependencies()
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleWatcher.destroyDependencies()
    }
}
