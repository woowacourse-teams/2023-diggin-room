package com.dygames.android_di.lifecycle

import androidx.lifecycle.ViewModel

class ActivityRetainedViewModel(private val lifecycleWatcher: LifecycleWatcher) : ViewModel() {
    override fun onCleared() {
        super.onCleared()
        lifecycleWatcher.destroyDependencies()
    }
}
