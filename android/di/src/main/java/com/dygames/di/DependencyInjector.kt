package com.dygames.di

import com.dygames.di.annotation.Injectable
import com.dygames.di.annotation.NotCaching
import com.dygames.di.annotation.Qualifier
import com.dygames.di.error.InjectError
import com.dygames.di.model.LifecycleAwareDependencies
import com.dygames.di.model.LifecycleAwareProviders
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KParameter
import kotlin.reflect.KType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure
import kotlin.reflect.typeOf

object DependencyInjector {
    private val providers: LifecycleAwareProviders = LifecycleAwareProviders()
    private val lifecycleAwareProviders: LifecycleAwareProviders = LifecycleAwareProviders()
    private val lifecycleAwareDependencies: LifecycleAwareDependencies =
        LifecycleAwareDependencies()

    inline fun <reified T : Any> inject(lifecycle: KType? = null): T {
        return inject(typeOf<T>(), lifecycle = lifecycle) as T
    }

    fun inject(
        type: KType,
        qualifier: Annotation? = null,
        lifecycle: KType? = null
    ): Any {
        return findDependency(type, qualifier) ?: instantiate(type, qualifier, lifecycle)
    }

    fun initDependencyInjector(init: LifecycleAwareProviders.() -> Unit) {
        providers.value.clear()
        lifecycleAwareDependencies.value.clear()
        lifecycleAwareProviders.value.clear()
        providers.apply(init)
        createDependencies(null)
    }

    fun createDependencies(lifecycle: KType?) {
        lifecycleAwareDependencies.put(lifecycle)
        lifecycleAwareProviders.put(lifecycle, providers.value[lifecycle])
    }

    fun addDependencies(type: KType, lifecycle: KType?, qualifier: Annotation?, dependency: Any) {
        lifecycleAwareDependencies.put(type, lifecycle, qualifier, dependency)
    }

    fun deleteDependencies(lifecycle: KType?) {
        lifecycleAwareProviders.remove(lifecycle)
        lifecycleAwareDependencies.remove(lifecycle)
    }

    private fun instantiate(
        type: KType,
        qualifier: Annotation? = null,
        lifecycle: KType? = null
    ): Any {
        val provider = lifecycleAwareProviders.value.values.firstNotNullOfOrNull {
            it.value.firstNotNullOfOrNull { provider ->
                val factory = provider.value.factories[type]
                    ?: return@firstNotNullOfOrNull provider.value.constructors[type]
                return factory()
            }
        }

        val constructor =
            type.jvmErasure.primaryConstructor ?: (
                provider?.jvmErasure?.primaryConstructor
                    ?: throw InjectError.ConstructorNoneAvailable(type)
                )

        val parameters = constructor.parameters
        val arguments = gatherArguments(parameters, lifecycle)
        val instance = constructor.call(*arguments)
        cacheInstance(type, lifecycle, qualifier, instance)
        injectFields(lifecycle, instance)
        return instance
    }

    private fun cacheInstance(
        type: KType,
        lifecycle: KType?,
        qualifier: Annotation?,
        instance: Any
    ) {
        if (type.jvmErasure.hasAnnotation<NotCaching>()) return
        lifecycleAwareDependencies.put(type, lifecycle, qualifier, instance)
    }

    private fun findDependency(type: KType, qualifier: Annotation?): Any? {
        val qualifiableDependencies = lifecycleAwareDependencies.find(type) ?: return null
        val dependencies = qualifiableDependencies.find(qualifier) ?: return null
        return dependencies.value[type]
    }

    private fun gatherArguments(parameters: List<KParameter>, lifecycle: KType? = null): Array<*> {
        return parameters.map { parameter ->
            val qualifier = findQualifier(parameter.annotations)
            inject(parameter.type, qualifier, lifecycle)
        }.toTypedArray()
    }

    private fun injectFields(lifecycle: KType?, instance: Any): Any {
        val fields = instance::class.declaredMemberProperties
        fields.filter { it.hasAnnotation<Injectable>() }.filterIsInstance<KMutableProperty<*>>()
            .forEach {
                val qualifier = findQualifier(it.annotations)
                it.setter.call(instance, inject(it.returnType, qualifier, lifecycle))
            }
        return instance
    }

    private fun findQualifier(annotations: List<Annotation>): Annotation? {
        return annotations.firstOrNull {
            it.annotationClass.hasAnnotation<Qualifier>()
        }
    }
}
