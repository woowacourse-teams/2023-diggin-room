package com.digginroom.digginroom

import com.digginroom.digginroom.viewmodels.JoinViewModel
import com.digginroom.repository.AccountRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class JoinViewModelTest {

    private lateinit var joinViewModel: JoinViewModel
    private lateinit var accountRepository: AccountRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        accountRepository = mockk()
        joinViewModel = JoinViewModel(accountRepository)
    }

    @Test
    fun `아이디 유효성 검증 실패 시 유효한 아이디인지에 대한 변수가 false로 설정된다`() {
        // given
        val id = INVALID_ID

        // when
        joinViewModel.validateId(id)

        // then
        assertEquals(joinViewModel.isValidId.value, false)
    }

    @Test
    fun `아이디 유효성 검증 실패 시 회원가입이 가능한지에 대한 여부를 나타내는 변수가 false로 설정된다`() {
        // given
        val id = INVALID_ID

        // when
        joinViewModel.validateId(id)

        // then
        assertEquals(joinViewModel.isJoinAble.value, false)
    }

    @Test
    fun `아이디 유효성 검증 성공 시 유효한 아이디인지에 대한 변수가 true로 설정된다`() {
        // given
        val id = INVALID_ID

        // when
        joinViewModel.validateId(id)

        // then
        assertEquals(joinViewModel.isValidId.value, true)
    }

    @Test
    fun `비밀번호 검증 실패 시 유효한 비밀번호인지에 대한 변수가 false로 설정된다`() {
        // given
        val password = INVALID_PASSWORD

        // when
        joinViewModel.validateId(password)

        // then
        assertEquals(joinViewModel.isValidPassword.value, false)
    }

    @Test
    fun `비밀번호 검증 실패 시 회원가입이 가능한지에 대한 여부를 나타내는 변수가 false로 설정된다`() {
        // given
        val password = INVALID_PASSWORD

        // when
        joinViewModel.validateId(password)

        // then
        assertEquals(joinViewModel.isJoinAble.value, false)
    }

    @Test
    fun `비밀번호 검증 성공 시 유효한 비밀번호인지에 대한 변수가 true로 설정된다`() {
        // given
        val password = INVALID_PASSWORD

        // when
        joinViewModel.validateId(password)

        // then
        assertEquals(joinViewModel.isValidPassword.value, true)
    }

    @Test
    fun `재입력한 비밀번호와 기존에 입력한 비밀번호가 일치하지 않을 시 비밀번호가 일치하는 지에 대한 상태를 나타내는 변수가 false로 설정된다`() {
        // given
        val password = INVALID_PASSWORD

        // when
        joinViewModel.validateId(password)

        // then
        assertEquals(joinViewModel.isValidPassword.value, true)
    }

    companion object {

        private const val INVALID_ID = "abc"
        private const val INVALID_PASSWORD = "123"
    }
}
