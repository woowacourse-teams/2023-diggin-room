package com.digginroom.digginroom.logging

interface LogResult<out T> {
    fun onSuccess(action: (T) -> Unit): LogResult<T>
    fun onFailure(action: (exception: Throwable) -> Unit): LogResult<T>
}
