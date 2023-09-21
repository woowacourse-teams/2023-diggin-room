package com.dygames.androiddi.lifecycle

import com.dygames.di.DependencyInjector
import kotlin.reflect.KType

open class LifecycleWatcher(val lifecycle: KType) {
    open fun createDependencies() {
        DependencyInjector.createDependencies(lifecycle)
    }

    fun destroyDependencies() {
        DependencyInjector.deleteDependencies(lifecycle)
    }
}
