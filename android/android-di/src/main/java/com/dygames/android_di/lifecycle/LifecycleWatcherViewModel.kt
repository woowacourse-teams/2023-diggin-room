package com.dygames.android_di.lifecycle

import androidx.lifecycle.ViewModel
import com.dygames.di.DependencyInjector
import kotlin.reflect.KType

abstract class LifecycleWatcherViewModel(val lifecycle: KType) : ViewModel() {
    lateinit var lifecycleWatcher: LifecycleWatcher

    inline fun <reified T : Any> inject(): T {
        return DependencyInjector.inject<T>(lifecycle)
    }

    override fun onCleared() {
        super.onCleared()
        lifecycleWatcher.destroyDependencies()
    }
}
