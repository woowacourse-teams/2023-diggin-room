package com.dygames.di.model

data class QualifiableProviders(
    val value: HashMap<Annotation?, Providers> = hashMapOf(null to Providers())
)
