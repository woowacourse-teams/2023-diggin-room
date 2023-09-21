package com.dygames.di.model

import kotlin.reflect.KType

data class LifecycleAwareDependencies(
    val value: HashMap<KType?, QualifiableDependencies> = hashMapOf()
) {
    fun put(lifecycle: KType?) {
        value[lifecycle] = QualifiableDependencies()
    }

    fun put(type: KType, lifecycle: KType?, qualifier: Annotation?, dependency: Any) {
        value[lifecycle]?.value?.get(qualifier)?.value?.set(
            type,
            dependency
        )
    }

    fun remove(lifecycle: KType?) {
        value.remove(lifecycle)
    }

    fun find(type: KType): QualifiableDependencies? {
        return value.values.firstOrNull { qualifiableDependencies ->
            qualifiableDependencies.value.values.firstOrNull { dependencies ->
                dependencies.value[type] != null
            } != null
        }
    }
}
