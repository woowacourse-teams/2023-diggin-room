package com.digginroom.digginroom

import com.digginroom.digginroom.logging.DefaultLogResult
import io.mockk.mockk

internal class LogResult {

    companion object {
        fun <T> success(value: T) = DefaultLogResult.success(value, mockk(relaxed = true))

        fun <T> failure() = DefaultLogResult.failure<T>(Throwable(), mockk(relaxed = true))
    }
}
