package com.dygames.di.model

import kotlin.reflect.KType

data class Providers(
    val constructors: HashMap<KType, KType> = hashMapOf(),
    val factories: HashMap<KType, () -> Any> = hashMapOf(),
)
