package com.digginroom.model.join

import com.digginroom.digginroom.model.user.JoinVerification
import com.digginroom.model.IdVerificationFixture
import com.digginroom.model.PasswordVerificationFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class JoinVerificationTest {

    @Test
    fun `아이디와 비밀번호 검증이 완료되지 않은 상태라면 회원가입 불가능한 상태이다`() {
        // given
        val idVerification = IdVerificationFixture.inProgress()
        val passwordVerification = PasswordVerificationFixture.complete()

        // when
        val joinVerification = JoinVerification(
            idVerification = idVerification,
            passwordVerification = passwordVerification
        )
        val actual = joinVerification.isJoinAble

        // then
        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun `아이디 검증이 완료되고 비밀번호 검증이 완료되지 않은 상태라면 회원가입 불가능한 상태이다`() {
        // given
        val idVerification = IdVerificationFixture.complete()
        val passwordVerification = PasswordVerificationFixture.inProgress()

        // when
        val joinVerification = JoinVerification(
            idVerification = idVerification,
            passwordVerification = passwordVerification
        )
        val actual = joinVerification.isJoinAble

        // then
        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun `아이디 검증이 완료되지 않고 비밀번호 검증이 완료된 상태라면 회원가입 불가능한 상태이다`() {
        // given
        val idVerification = IdVerificationFixture.inProgress()
        val passwordVerification = PasswordVerificationFixture.complete()

        // when
        val joinVerification = JoinVerification(
            idVerification = idVerification,
            passwordVerification = passwordVerification
        )
        val actual = joinVerification.isJoinAble

        // then
        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun `아이디 검증이 완료되고 비밀번호 검증이 완료된 상태라면 회원가입 가능한 상태이다`() {
        // given
        val idVerification = IdVerificationFixture.inProgress()
        val passwordVerification = PasswordVerificationFixture.complete()

        // when
        val joinVerification = JoinVerification(
            idVerification = idVerification,
            passwordVerification = passwordVerification
        )
        val actual = joinVerification.isJoinAble

        // then
        val expected = false

        assertEquals(expected, actual)
    }
}
