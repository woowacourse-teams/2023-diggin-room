package com.digginroom.model.join

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class PasswordTest {

    @Test
    fun `비밀번호의 길이가 8자 미만이면 예외가 발생한다`() {
        // given
        val password = "woog"

        // then
        assertThrows<IllegalArgumentException> { Password(password) }
    }

    @Test
    fun `비밀번호의 길이가 8자 이상 15자 이하여도 영문과 숫자가 하나씩 포함되어있지 않은 경우 예외가 발생한다`() {
        // given
        val password = "kimjinuk"

        // then
        assertThrows<IllegalArgumentException> { Password(password) }
    }

    @Test
    fun `비밀번호의 길이가 8자 이상 15자 이하이고 영문과 특수문자를 반드시 포함해야한다`() {
        // given
        val password = "kimjinuk99"

        // then
        assertDoesNotThrow { Password(password) }
    }

    @Test
    fun `비밀번호의 길이가 15자 초과이면 예외가 발생한다`() {
        // given
        val password = "digginroompassword"

        // then
        assertThrows<IllegalArgumentException> { Password(password) }
    }
}
