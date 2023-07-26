package com.digginroom.digginroom.viewmodels

import androidx.lifecycle.MutableLiveData

class NonNullMutableLiveData<T : Any>(value: T) : MutableLiveData<T>(value) {

    override fun getValue(): T {
        return super.getValue()!!
    }
}
