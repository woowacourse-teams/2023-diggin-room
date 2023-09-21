package com.dygames.android_di

import com.dygames.android_di.lifecycle.LifecycleWatcher
import kotlin.reflect.typeOf

class FakeActivity : LifecycleWatcher(typeOf<FakeActivity>()) {
    fun onCreate() {
        createDependencies()
    }

    fun onDestroy() {
        destroyDependencies()
    }
}

class FakeDefaultDependency : FakeDependency
interface FakeDependency

class FakeViewModel(val fakeDependency: FakeDependency)
