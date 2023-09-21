package com.dygames.di.model

import kotlin.reflect.KType

data class Dependencies(
    val value: HashMap<KType, Any> = hashMapOf()
)
