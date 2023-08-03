package com.digginroom.digginroom.logging

import android.util.Log

class ConsoleLogger : Logger {

    override fun log(logMessage: LogMessage) {
        when (logMessage.level) {
            LogLevel.DEBUG -> Log.d(logMessage.tag, logMessage.body)
            LogLevel.ERROR -> Log.e(logMessage.tag, logMessage.body)
            LogLevel.INFO -> Log.i(logMessage.tag, logMessage.body)
            LogLevel.WARNING -> Log.w(logMessage.tag, logMessage.body)
        }
    }
}
