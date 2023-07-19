package com.digginroom.model.join

import com.digginroom.digginroom.model.user.Password
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.lang.IllegalArgumentException

class PasswordTest {

    @Test
    fun `비밀번호의 길이는 8자 이상 16자 이하이고 영문, 숫자 특수문자를 반드시 포함해야한다`() {
        // given
        val password = "kimjinuk99!"

        // then
        assertDoesNotThrow { Password(password) }
    }

    @Test
    fun `비밀번호의 길이가 8자 미만이면 예외가 발생한다`() {
        // given
        val password = "woogi9!"

        // then
        assertThrows<IllegalArgumentException> { Password(password) }
    }

    @ParameterizedTest
    @ValueSource(strings = ["kimjinuk99", "qwer!!abc", "991006!!"])
    fun `비밀번호의 길이가 8자 이상 15자 이하여도 영문, 숫자, 특수문자가 하나씩 포함되어있지 않은 경우 예외가 발생한다`(password: String) {
        // then
        assertThrows<IllegalArgumentException> { Password(password) }
    }

    @ParameterizedTest
    @ValueSource(strings = ["내비밀번호는", "【abcdef", "\\"])
    fun `비밀번호가 영문, 숫자, 특수문자 이외의 문자들을 포함하고 있는 경우 예외가 발생한다`(password: String) {
        // then
        assertThrows<IllegalArgumentException> { Password(password) }
    }

    @Test
    fun `비밀번호의 길이가 16자를 초과하면 예외가 발생한다`() {
        // given
        val password = "digginroompasswor"

        // then
        assertThrows<IllegalArgumentException> { Password(password) }
    }
}
