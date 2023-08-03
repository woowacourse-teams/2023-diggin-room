package com.digginroom.digginroom.logging

class DefaultLogResult<out T> private constructor(
    val value: T? = null,
    private val exception: Throwable? = null,
    private val loggers: Loggers
) : LogResult<T> {

    override fun onFailure(action: (exception: Throwable) -> Unit): DefaultLogResult<T> {
        if (exception == null) return this
        loggers.log(LogMessage(LogLevel.ERROR, FAILURE, exception.message ?: ""))
        loggers.log(LogMessage(LogLevel.ERROR, FAILURE, exception.stackTraceToString()))
        action(exception)
        return this
    }

    override fun onSuccess(action: (value: T) -> Unit): DefaultLogResult<T> {
        if (value == null) return this
        loggers.log(LogMessage(LogLevel.INFO, SUCCESS, value.toString()))
        action(value)
        return this
    }

    companion object {
        private const val SUCCESS = "Success"
        private const val FAILURE = "Failure"

        fun <T> success(value: T, loggers: Loggers = DefaultLoggers): DefaultLogResult<T> =
            DefaultLogResult(value = value, loggers = loggers)

        fun <T> failure(
            exception: Throwable,
            loggers: Loggers = DefaultLoggers
        ): DefaultLogResult<T> =
            DefaultLogResult(exception = exception, loggers = loggers)
    }
}
