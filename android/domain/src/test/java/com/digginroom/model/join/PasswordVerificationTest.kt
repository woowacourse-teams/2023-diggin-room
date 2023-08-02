package com.digginroom.model.join

import com.digginroom.digginroom.model.user.PasswordVerification
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PasswordVerificationTest {

    private lateinit var passwordVerification: PasswordVerification

    @BeforeEach
    fun setUp() {
        passwordVerification = PasswordVerification()
    }

    @Test
    fun `password가 규칙에 맞는 형태인지 확인하고 규칙에 맞지 않는 경우 유효하지 않은 상태이다`() {
        // given
        val password = INVALID_PASSWORD

        // when
        val actual = passwordVerification.checkIsValid(password).isValid

        // then
        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun `password가 규칙에 맞는 형태인지 확인하고 규칙에 맞는 경우 유효한 상태이다`() {
        // given
        val password = VALID_PASSWORD

        // when
        val actual = passwordVerification.checkIsValid(password).isValid

        // then
        val expected = true

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `password가 처음으로 입력한 비밀번호와 재입력한 비밀번호와의 동일성 여부를 설정한다`(isEqualReInputPassword: Boolean) {
        // given

        // when
        val actual = passwordVerification.setIsEqualReInput(isEqualReInputPassword).isEqualReInput

        // then
        val expected = isEqualReInputPassword

        assertEquals(expected, actual)
    }

    @Test
    fun `password 규칙 검사와 재입력한 비밀번호와의 동일성 여부가 검증이 되면 검증된 비밀번호이다`() {
        // given
        val password = VALID_PASSWORD

        // when
        val actual = passwordVerification.checkIsValid(password)
            .setIsEqualReInput(true)
            .isValid

        // then
        val expected = true

        assertEquals(expected, actual)
    }

    companion object {

        private const val INVALID_PASSWORD = "abc"
        private const val VALID_PASSWORD = "test1234!"
    }
}
