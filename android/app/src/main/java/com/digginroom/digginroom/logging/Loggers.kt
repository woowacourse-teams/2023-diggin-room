package com.digginroom.digginroom.logging

interface Loggers {

    val value: List<Logger>

    fun log(logMessage: LogMessage)
}
