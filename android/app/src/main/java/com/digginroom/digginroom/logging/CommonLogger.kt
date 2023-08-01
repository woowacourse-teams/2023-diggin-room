package com.digginroom.digginroom.logging

object CommonLogger {
    private val loggers: List<Logger> = listOf(
        ConsoleLogger(),
        FirebaseLogger()
    )
    fun log(logMessage: LogMessage) {
        Result
        loggers.forEach {
            it.log(logMessage)
        }
    }
}
