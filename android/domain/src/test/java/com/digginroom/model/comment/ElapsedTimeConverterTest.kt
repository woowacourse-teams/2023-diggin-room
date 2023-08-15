package com.digginroom.model.comment

import com.digginroom.digginroom.model.comment.ElapsedTimeConverter
import com.digginroom.digginroom.model.comment.TimeUnit
import com.digginroom.digginroom.model.comment.TimeUnitValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ElapsedTimeConverterTest {
    @Test
    fun 시간_차이가_1분이_안된다면_초_단위로_표시한다() {
        val seconds = 30L
        val result = ElapsedTimeConverter.convert(seconds)
        assertEquals(TimeUnitValue(TimeUnit.SEC, 30L), result)
    }

    @Test
    fun 시간_차이가_1분_이상_1시간_이하라면_분_단위로_표시한다() {
        val seconds = 300L
        val result = ElapsedTimeConverter.convert(seconds)
        assertEquals(TimeUnitValue(TimeUnit.MIN, 5L), result)
    }

    @Test
    fun 시간_차이가_1시간_이상_1일_이하라면_시간_단위로_표시한다() {
        val seconds = 7200L
        val result = ElapsedTimeConverter.convert(seconds)
        assertEquals(TimeUnitValue(TimeUnit.HOUR, 2L), result)
    }

    @Test
    fun 시간_차이가_1일_이상_1주_이하라면_일_단위로_표시한다() {
        val seconds = 172800L
        val result = ElapsedTimeConverter.convert(seconds)
        assertEquals(TimeUnitValue(TimeUnit.DAY, 2L), result)
    }

    @Test
    fun 시간_차이가_1주_이상_1개월_이하라면_주_단위로_표시한다() {
        val seconds = 1209600L
        val result = ElapsedTimeConverter.convert(seconds)
        assertEquals(TimeUnitValue(TimeUnit.WEEK, 2L), result)
    }

    @Test
    fun 시간_차이가_1개월_이상_1년_이하라면_개월_단위로_표시한다() {
        val seconds = 5184000L
        val result = ElapsedTimeConverter.convert(seconds)
        assertEquals(TimeUnitValue(TimeUnit.MONTH, 2L), result)
    }

    @Test
    fun 시간_차이가_1년_이상이라면_년_단위로_표시한다() {
        val seconds = 63072000L
        val result = ElapsedTimeConverter.convert(seconds)
        assertEquals(TimeUnitValue(TimeUnit.YEAR, 2L), result)
    }
}
