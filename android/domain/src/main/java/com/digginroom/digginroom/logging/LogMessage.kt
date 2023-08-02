package com.digginroom.digginroom.logging

data class LogMessage(
    val level: LogLevel,
    val tag: String,
    val body: String
)
