package com.digginroom.model.join

import com.digginroom.model.user.Id
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.lang.IllegalArgumentException

class IdTest {

    @Test
    fun `ID의 길이는 5자 이상 11자 이하의 영문 혹은 숫자로 이루어진다`() {
        // given
        val id = "kimjinuk99"

        // then
        assertDoesNotThrow { Id(id) }
    }

    @Test
    fun `ID의 길이가 5자 미만인 경우 예외를 던진다`() {
        // given
        val id = "wook"

        // then
        assertThrows<IllegalArgumentException> { Id(id) }
    }

    @Test
    fun `ID의 길이가 11자 초과인 경우 예외를 던진다`() {
        // given
        val id = "digginroomtestid"

        // then
        assertThrows<IllegalArgumentException> { Id(id) }
    }

    @ParameterizedTest
    @ValueSource(strings = ["내아이디는한글", "sok 0220", "jinuk99!"])
    fun `ID가 영문 숫자로 이루어져있지 않은 경우 예외를 던진다`(id: String) {
        assertThrows<IllegalArgumentException> { Id(id) }
    }
}
