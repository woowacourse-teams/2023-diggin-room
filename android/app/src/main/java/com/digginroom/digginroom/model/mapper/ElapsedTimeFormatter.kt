package com.digginroom.digginroom.model.mapper

import com.digginroom.digginroom.model.comment.ElapsedTimeConverter
import com.digginroom.digginroom.model.comment.TimeUnit
import com.digginroom.digginroom.model.comment.TimeUnitValue

object ElapsedTimeFormatter {
    fun convert(durationSeconds: Long): String {
        val timeUnitValue: TimeUnitValue = ElapsedTimeConverter.convert(durationSeconds)
        val unitFormatter = when (timeUnitValue.unit) {
            TimeUnit.SEC -> "%d초 전"
            TimeUnit.MIN -> "%d분 전"
            TimeUnit.HOUR -> "%d시간 전"
            TimeUnit.DAY -> "%d일 전"
            TimeUnit.WEEK -> "%d주 전"
            TimeUnit.MONTH -> "%d개월 전"
            TimeUnit.YEAR -> "%d년 전"
        }
        return unitFormatter.format(if (timeUnitValue.value < 0) 0 else timeUnitValue.value)
    }
}
