package com.dygames.androiddi

import com.dygames.androiddi.lifecycle.LifecycleWatcher
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
