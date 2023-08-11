package com.digginroom.digginroom.model.comment

class ElapsedTimeConverter {

    companion object {
        fun convert(seconds: Long): TimeUnitValue {
            return when {
                seconds < Seconds.MIN.value -> TimeUnitValue(TimeUnit.SEC, seconds)
                seconds < Seconds.HOUR.value -> TimeUnitValue(TimeUnit.MIN, seconds / Seconds.MIN.value)
                seconds < Seconds.DAY.value -> TimeUnitValue(TimeUnit.HOUR, seconds / Seconds.HOUR.value)
                seconds < Seconds.WEEK.value -> TimeUnitValue(TimeUnit.DAY, seconds / Seconds.DAY.value)
                seconds < Seconds.MONTH.value -> TimeUnitValue(TimeUnit.WEEK, seconds / Seconds.WEEK.value)
                seconds < Seconds.YEAR.value -> TimeUnitValue(TimeUnit.MONTH, seconds / Seconds.MONTH.value)
                else -> TimeUnitValue(TimeUnit.YEAR, seconds / Seconds.YEAR.value)
            }
        }
    }
}
