package com.digginroom.digginroom.feature.room.customview.roominfoview

import com.digginroom.digginroom.model.comment.ElapsedTimeConverter
import com.digginroom.digginroom.model.comment.TimeUnit

object ElapsedTimeFormatter {
    fun convert(durationSeconds: Long): String {
        val timeUnitValue = ElapsedTimeConverter.convert(durationSeconds)
        val unitFormatter = when (timeUnitValue.unit) {
            TimeUnit.SEC -> "%d 초 전"
            TimeUnit.MIN -> "%d 분 전"
            TimeUnit.HOUR -> "%d 시간 전"
            TimeUnit.DAY -> "%d 일 전"
            TimeUnit.WEEK -> "%d 주 전"
            TimeUnit.MONTH -> "%d 개월 전"
            TimeUnit.YEAR -> "%d 년 전"
        }
        return unitFormatter.format(timeUnitValue.value)
    }
}
