package com.dygames.androiddi.lifecycle

import androidx.lifecycle.ViewModel

class ActivityRetainedViewModel(private val lifecycleWatcher: LifecycleWatcher) : ViewModel() {
    override fun onCleared() {
        super.onCleared()
        lifecycleWatcher.destroyDependencies()
    }
}
