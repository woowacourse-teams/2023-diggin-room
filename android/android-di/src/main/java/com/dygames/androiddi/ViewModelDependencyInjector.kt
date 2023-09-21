package com.dygames.androiddi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dygames.androiddi.lifecycle.LifecycleWatcher
import com.dygames.androiddi.lifecycle.LifecycleWatcherViewModel
import com.dygames.di.DependencyInjector.inject
import kotlin.reflect.typeOf

object ViewModelDependencyInjector {
    inline fun <reified T : LifecycleWatcherViewModel> injectLifecycleAwareViewModel(): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                val lifecycleWatcher = LifecycleWatcher(typeOf<T>())
                lifecycleWatcher.createDependencies()
                inject<T>().apply {
                    this.lifecycleWatcher = lifecycleWatcher
                }
            }
        }

    inline fun <reified T : ViewModel> injectViewModel(): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                inject<T>()
            }
        }
}
