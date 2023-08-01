package com.digginroom.digginroom.logging

class DefaultLogResult<out T>(
    val value: T? = null,
    private val exception: Throwable? = null
) : LogResult<T> {

    override fun onFailure(action: (exception: Throwable) -> Unit): DefaultLogResult<T> {
        if (exception == null) return this
        CommonLogger.log(LogMessage(LogLevel.ERROR, FAILURE, exception.message ?: ""))
        CommonLogger.log(LogMessage(LogLevel.ERROR, FAILURE, exception.stackTraceToString()))
        action(exception)
        return this
    }

    override fun onSuccess(action: (value: T) -> Unit): DefaultLogResult<T> {
        if (value == null) return this
        CommonLogger.log(LogMessage(LogLevel.INFO, SUCCESS, value.toString()))
        action(value)
        return this
    }

    companion object {
        private const val SUCCESS = "Success"
        private const val FAILURE = "Failure"

        fun <T> success(value: T): DefaultLogResult<T> = DefaultLogResult(value = value)
        fun <T> failure(exception: Throwable): DefaultLogResult<T> =
            DefaultLogResult(exception = exception)
    }
}
