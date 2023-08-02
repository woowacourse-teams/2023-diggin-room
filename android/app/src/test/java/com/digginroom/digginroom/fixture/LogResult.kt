package com.digginroom.digginroom.fixture

import com.digginroom.digginroom.logging.DefaultLogResult
import io.mockk.mockk

class LogResult {

    companion object {
        fun <T> success(value: T) = DefaultLogResult.success(value, mockk(relaxed = true))

        fun <T> failure(exception: Throwable = Throwable()) = DefaultLogResult.failure<T>(exception, mockk(relaxed = true))
    }
}
