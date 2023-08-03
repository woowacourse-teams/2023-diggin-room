package com.digginroom.digginroom.logging

object DefaultLoggers : Loggers {

    override val value: List<Logger> = listOf(
        ConsoleLogger(),
        FirebaseLogger()
    )

    override fun log(logMessage: LogMessage) {
        value.forEach {
            it.log(logMessage)
        }
    }
}
