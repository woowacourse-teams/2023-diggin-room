package com.dygames.di.model

data class QualifiableDependencies(
    val value: HashMap<Annotation?, Dependencies> = hashMapOf(null to Dependencies())
) {
    fun find(qualifier: Annotation?): Dependencies? {
        return value[qualifier]
    }
}
