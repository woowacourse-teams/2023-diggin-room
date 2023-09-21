package com.dygames.di.model

import kotlin.reflect.KType

data class LifecycleAwareProviders(
    val value: HashMap<KType?, QualifiableProviders> = hashMapOf(null to QualifiableProviders())
) {
    fun put(lifecycle: KType?, providers: QualifiableProviders?) {
        value[lifecycle] = providers ?: return
    }

    fun remove(lifecycle: KType?) {
        value.remove(lifecycle)
    }
}
