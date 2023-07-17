package com.digginroom.model.join

import com.digginroom.model.user.Id
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class IdTest {

    @Test
    fun `ID의 길이가 5자 미만인 경우 예외를 던진다`() {
        // given
        val id = "wook"

        // then
        assertThrows<IllegalArgumentException> { Id(id) }
    }

    @Test
    fun `ID의 길이는 5자 이상 11자 이하이다`() {
        // given
        val id = "kimjinuk99"

        // then
        assertDoesNotThrow { Id(id) }
    }

    @Test
    fun `ID의 길이가 11자 초과인 경우 예외를 던진다`() {
        // given
        val id = "digginroomtestid"

        // then
        assertThrows<IllegalArgumentException> { Id(id) }
    }
}
