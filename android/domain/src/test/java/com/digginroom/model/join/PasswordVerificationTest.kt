package com.digginroom.model.join

import com.digginroom.digginroom.model.user.PasswordVerification
import com.digginroom.model.AccountFixture.INVALID_PASSWORD
import com.digginroom.model.AccountFixture.VALID_PASSWORD
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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

    @Test
    fun `password가 규칙에 맞지 않는 형태라면 검증되지 않은 password이다`() {
        // given
        val password = INVALID_PASSWORD

        // when
        val actual = passwordVerification.checkIsValid(password).isVerified

        // then
        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun `기존 password와 재입력한 password가 일치하지 않는다면 동일하지 않은 비밀번호이다`() {
        // given
        val inputPassword = VALID_PASSWORD
        val reInputPassword = INVALID_PASSWORD

        // when
        val actual = passwordVerification.checkIsEqualReInput(
            password = inputPassword,
            reInputPassword = reInputPassword
        ).isEqualReInput

        // then
        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun `기존 password와 재입력한 password가 일치하지 않는다면 검증되지 않은 비밀번호이다`() {
        // given
        val inputPassword = VALID_PASSWORD
        val reInputPassword = INVALID_PASSWORD

        // when
        val actual = passwordVerification.checkIsEqualReInput(
            password = inputPassword,
            reInputPassword = reInputPassword
        ).isVerified

        // then
        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun `password 규칙 검사와 재입력한 비밀번호와의 동일성 여부가 검증이 되면 검증된 비밀번호이다`() {
        // given
        val inputPassword = VALID_PASSWORD
        val reInputPassword = VALID_PASSWORD

        // when
        val actual = passwordVerification.checkIsEqualReInput(
            password = inputPassword,
            reInputPassword = reInputPassword
        ).isEqualReInput

        // then
        val expected = true

        assertEquals(expected, actual)
    }
}
