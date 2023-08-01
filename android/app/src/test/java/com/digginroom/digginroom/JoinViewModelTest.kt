package com.digginroom.digginroom

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.digginroom.digginroom.feature.join.JoinViewModel
import com.digginroom.digginroom.repository.AccountRepository
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher

class JoinViewModelTest : TestWatcher() {

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
    fun `아이디 유효성 검증 성공 시 유효한 아이디인지에 대한 변수가 true로 설정된다`() {
        // given
        val id = VALID_ID

        // when
        joinViewModel.validateId(id)

        // then
        assertEquals(joinViewModel.isValidId.value, true)
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

//    @Test
//    fun `중복된 아이디인 경우에 아이디가 중복인지를 나타내는 변수가 true로 설정된다`() = runTest(UnconfinedTestDispatcher()) {
//        // given
//        val id = Id("jinuk99")
//
//        every {
//            runBlocking {
//                accountRepository.fetchIsDuplicatedId(id)
//            }
//        } returns Result.success(true)
//
//        // when
//        joinViewModel.validateIdRedundancy(id.value)
//
//        // then
//        assertEquals(joinViewModel.isJoinAble.value, false)
//    }

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
        val password = VALID_PASSWORD

        // when
        joinViewModel.validatePassword(password)

        // then
        assertEquals(joinViewModel.isValidPassword.value, true)
    }

    @Test
    fun `재입력한 비밀번호와 기존에 입력한 비밀번호가 일치하지 않을 시 비밀번호가 일치하는 지에 대한 상태를 나타내는 변수가 false로 설정된다`() {
        // given
        val password = VALID_PASSWORD
        val reInputPassword = VALID_PASSWORD

        // when
        joinViewModel.validatePasswordEquality(
            password = password,
            reInputPassword = reInputPassword,
        )

        // then
        assertEquals(joinViewModel.isEqualPassword.value, true)
    }

    companion object {

        private const val VALID_ID = "jinuk99"
        private const val INVALID_ID = "abc"
        private const val INVALID_PASSWORD = "123"
        private const val VALID_PASSWORD = "s7730857"
    }
}
