package com.dygames.di

import com.dygames.di.model.LifecycleAwareProviders
import com.dygames.di.model.Providers
import com.dygames.di.model.QualifiableProviders
import kotlin.reflect.KType
import kotlin.reflect.typeOf

inline fun <reified T : Any> Providers.provider(noinline init: () -> T) {
    factories[typeOf<T>()] = init
}

inline fun <reified T : Any> Providers.provider(type: KType) {
    constructors[typeOf<T>()] = type
}

inline fun <reified T : Any> QualifiableProviders.provider(noinline init: () -> T) {
    value.getOrPut(null) {
        Providers()
    }.apply {
        provider(init)
    }
}

inline fun <reified T : Any> QualifiableProviders.provider(type: KType) {
    value.getOrPut(null) {
        Providers()
    }.apply {
        provider<T>(type)
    }
}

inline fun <reified T : Any> LifecycleAwareProviders.provider(noinline init: () -> T) {
    value.getOrPut(null) {
        QualifiableProviders()
    }.value.getOrPut(null) {
        Providers()
    }.apply {
        provider(init)
    }
}

inline fun <reified T : Any> LifecycleAwareProviders.provider(type: KType) {
    value.getOrPut(null) {
        QualifiableProviders()
    }.value.getOrPut(null) {
        Providers()
    }.apply {
        provider<T>(type)
    }
}

fun QualifiableProviders.qualifier(annotation: Annotation? = null, init: Providers.() -> Unit) {
    value.getOrPut(annotation) {
        Providers()
    }.apply(init)
}

fun LifecycleAwareProviders.qualifier(annotation: Annotation? = null, init: Providers.() -> Unit) {
    value.getOrPut(null) {
        QualifiableProviders()
    }.value.getOrPut(annotation) {
        Providers()
    }.apply(init)
}

inline fun <reified T> LifecycleAwareProviders.lifecycle(init: QualifiableProviders.() -> Unit) {
    value.getOrPut(typeOf<T>()) {
        QualifiableProviders()
    }.apply(init)
}

fun dependencies(
    init: LifecycleAwareProviders.() -> Unit
) {
    DependencyInjector.initDependencyInjector(init)
}
