package com.digginroom.model.join

import com.digginroom.digginroom.model.user.IdVerification
import com.digginroom.model.AccountFixture.INVALID_ID
import com.digginroom.model.AccountFixture.VALID_ID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class IdVerificationTest {

    private lateinit var idVerification: IdVerification

    @BeforeEach
    fun setUp() {
        idVerification = IdVerification()
    }

    @Test
    fun `id가 규칙에 맞는 형태인지 확인하고 규칙에 맞지 않는 경우 유효하지 않은 상태이다`() {
        // given
        val id = INVALID_ID

        // when
        val actual = idVerification.checkIsValid(id)

        // then
        val expected = false

        assertEquals(expected, actual.isValid)
    }

    @Test
    fun `id가 규칙에 맞는 형태인지 확인하고 규칙에 맞는 경우 유효한 상태이다`() {
        // given
        val id = VALID_ID

        // when
        val actual = idVerification.checkIsValid(id).isValid

        // then
        val expected = true

        assertEquals(expected, actual)
    }

    @Test
    fun `id가 규칙에 맞지 않는 형태라면 검증된 id가 아니다`() {
        // given
        val id = VALID_ID

        // when
        val actual = idVerification.checkIsValid(id).isVerified

        // then
        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun `id 중복 검사가 이루어진 상태가 아니라면 검증된 id가 아니다`() {
        // given
        val id = VALID_ID

        // when
        val actual = IdVerification(
            isValid = true,
            isCheckedDuplication = false
        ).isVerified

        // then
        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun `id 규칙 검사, 중복 검사에 대한 여부, 중복이 아닌지에 대한 여부가 검증이 되었다면 검증된 아이디이다`() {
        // given
        val id = VALID_ID

        // when
        val actual = idVerification.checkIsValid(id)
            .copy(isCheckedDuplication = true)
            .copy(isDuplicated = false)
            .isVerified

        // then
        val expected = true

        assertEquals(expected, actual)
    }
}
