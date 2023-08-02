package com.digginroom.model.join

import com.digginroom.digginroom.model.user.IdVerification
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

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

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `id 중복 검사 여부를 설정한다`(isCheckedDuplication: Boolean) {
        // given

        // when
        val actual = idVerification.setIsCheckedDuplication(isCheckedDuplication)
            .isCheckedDuplication

        // then
        val expected = isCheckedDuplication

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `id가 중복이 됐는지에 대한 여부를 설정한다`(isDuplicated: Boolean) {
        // given

        // when
        val actual = idVerification.setIsDuplicated(isDuplicated).isDuplicated

        // then
        val expected = isDuplicated

        assertEquals(expected, actual)
    }

    @Test
    fun `id 규칙 검사, 중복 검사에 대한 여부, 중복이 아닌지에 대한 여부가 검증이 되었다면 검증된 아이디이다`() {
        // given
        val id = VALID_ID

        // when
        val actual = idVerification.checkIsValid(id)
            .setIsCheckedDuplication(true)
            .setIsDuplicated(false)
            .isVerified

        // then
        val expected = true

        assertEquals(expected, actual)
    }

    companion object {

        private const val INVALID_ID = "abc"
        private const val VALID_ID = "jinuk99"
    }
}
