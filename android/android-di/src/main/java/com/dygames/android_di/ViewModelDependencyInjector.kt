package com.dygames.android_di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dygames.android_di.lifecycle.LifecycleWatcher
import com.dygames.android_di.lifecycle.LifecycleWatcherViewModel
import com.dygames.di.DependencyInjector.inject
import kotlin.reflect.typeOf

object ViewModelDependencyInjector {
    inline fun <reified T : LifecycleWatcherViewModel> injectViewModel(): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                val lifecycleWatcher = LifecycleWatcher(typeOf<T>())
                lifecycleWatcher.createDependencies()
                inject<T>().apply {
                    this.lifecycleWatcher = lifecycleWatcher
                }
            }
        }
}
