package com.digginroom.digginroom.logging

inline fun <T, R> T.logRunCatching(block: T.() -> R): DefaultLogResult<R> {
    return try {
        DefaultLogResult.success(block())
    } catch (e: Throwable) {
        DefaultLogResult.failure(e)
    }
}
