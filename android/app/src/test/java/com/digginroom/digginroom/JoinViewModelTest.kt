package com.digginroom.digginroom

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.digginroom.digginroom.feature.join.JoinState
import com.digginroom.digginroom.feature.join.JoinViewModel
import com.digginroom.digginroom.logging.DefaultLogResult
import com.digginroom.digginroom.model.user.Account
import com.digginroom.digginroom.model.user.Id
import com.digginroom.digginroom.model.user.Password
import com.digginroom.digginroom.repository.AccountRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class JoinViewModelTest {

    private lateinit var joinViewModel: JoinViewModel
    private lateinit var accountRepository: AccountRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        accountRepository = mockk()
        joinViewModel = JoinViewModel(accountRepository)

//        mockkStatic(Log::class)
//        every { Log.v(any(), any()) } returns 0
//        every { Log.d(any(), any()) } returns 0
//        every { Log.i(any(), any()) } returns 0
//        every { Log.e(any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `아이디 유효성 검증 성공 시 유효한 아이디인지에 대한 변수가 true로 설정된다`() {
        // given
        val id = VALID_ID

        joinViewModel.id.value = id

        // when
        joinViewModel.validateId()

        // then
        assertEquals(joinViewModel.isValidId.value, true)
    }

    @Test
    fun `아이디 유효성 검증 실패 시 유효한 아이디인지에 대한 변수가 false로 설정된다`() {
        // given
        val id = INVALID_ID

        joinViewModel.id.value = id

        // when
        joinViewModel.validateId()

        // then
        assertEquals(joinViewModel.isValidId.value, false)
    }

    @Test
    fun `아이디 유효성 검증 실패 시 회원가입 가능 여부를 나타내는 변수가 false로 설정된다`() {
        // given
        val id = INVALID_ID

        joinViewModel.id.value = id

        // when
        joinViewModel.validateId()

        // then
        assertEquals(joinViewModel.isJoinAble.value, false)
    }

    @Test
    fun `아이디 중복 검사를 한 경우에 검사를 했는지에 대한 여부를 나타내는 변수가 true로 설정된다`() {
        // given
        val id = Id(EXAMPLE_ID)

        coEvery {
            accountRepository.fetchIsDuplicatedId(id)
        } returns DefaultLogResult.success(true)

        joinViewModel.id.value = id.value

        // when
        joinViewModel.validateIdRedundancy()

        // then
        assertEquals(joinViewModel.isUniqueId.value, false)
    }

    @Test
    fun `중복된 아이디인 경우에 아이디가 중복인지를 나타내는 변수가 true로 설정된다`() {
        // given
        val id = Id(DUPLICATED_ID)

        coEvery {
            accountRepository.fetchIsDuplicatedId(id)
        } returns DefaultLogResult.success(true)

        joinViewModel.id.value = id.value

        // when
        joinViewModel.validateIdRedundancy()

        // then
        assertEquals(joinViewModel.isUniqueId.value, false)
    }

    @Test
    fun `중복된 아이디인 경우에 아이디가 회원가입 가능 여부를 나타내는 변수가 false로 설정된다`() {
        // given
        val id = Id(DUPLICATED_ID)

        coEvery {
            accountRepository.fetchIsDuplicatedId(id)
        } returns DefaultLogResult.success(true)

        joinViewModel.id.value = id.value

        // when
        joinViewModel.validateIdRedundancy()

        // then
        assertEquals(joinViewModel.isJoinAble.value, false)
    }

    @Test
    fun `비밀번호 검증 실패 시 유효한 비밀번호인지에 대한 변수가 false로 설정된다`() {
        // given
        val password = INVALID_PASSWORD

        joinViewModel.password.value = password

        // when
        joinViewModel.validatePassword()

        // then
        assertEquals(joinViewModel.isValidPassword.value, false)
    }

    @Test
    fun `비밀번호 검증 실패 시 회원가입 가능 여부를 나타내는 변수가 false로 설정된다`() {
        // given
        val password = INVALID_PASSWORD

        joinViewModel.password.value = password

        // when
        joinViewModel.validatePassword()

        // then
        assertEquals(joinViewModel.isJoinAble.value, false)
    }

    @Test
    fun `비밀번호 검증 성공 시 유효한 비밀번호인지에 대한 변수가 true로 설정된다`() {
        // given
        val password = VALID_PASSWORD

        joinViewModel.password.value = password

        // when
        joinViewModel.validatePassword()

        // then
        assertEquals(true, joinViewModel.isValidPassword.value)
    }

    @Test
    fun `재입력한 비밀번호와 기존에 입력한 비밀번호가 일치하지 않을 시 비밀번호가 일치하는 지에 대한 상태를 나타내는 변수가 false로 설정된다`() {
        // given
        val password = VALID_PASSWORD
        val reInputPassword = VALID_PASSWORD

        joinViewModel.password.value = password
        joinViewModel.reInputPassword.value = reInputPassword

        // when
        joinViewModel.isEqualPassword

        // then
        assertEquals(joinViewModel.isEqualPassword.value, true)
    }

    @Test
    fun `재입력한 비밀번호와 기존에 입력한 비밀번호가 일치하지 않는 경우 회원가입 가능 여부를 나타내는 변수가 false로 설정된다`() {
        // given
        val password = VALID_PASSWORD
        val reInputPassword = INVALID_PASSWORD

        joinViewModel.password.value = password
        joinViewModel.reInputPassword.value = reInputPassword

        // when
        joinViewModel.validatePasswordEquality()

        // then
        assertEquals(joinViewModel.isJoinAble.value, false)
    }

    @Test
    fun `회원가입 성공시 회원가입 상태를 나타내는 변수가 suceed로 설정된다`() {
        // given
        val id = Id(VALID_ID)
        val password = Password(VALID_PASSWORD)

        coEvery {
            accountRepository.postJoin(
                Account(
                    id = id,
                    password = password
                )
            )
        } returns DefaultLogResult.success(Unit)

        joinViewModel.id.value = id.value
        joinViewModel.password.value = password.value

        // when
        joinViewModel.join()

        // then
        assertEquals(JoinState.Succeed, joinViewModel.state.value)
    }

    @Test
    fun `회원가입 실패시 회원가입 상태를 나타내는 변수가 failed로 설정된다`() {
        // given
        val id = Id(EXAMPLE_ID)
        val password = Password(EXAMPLE_PASSWORD)

        coEvery {
            accountRepository.postJoin(
                Account(
                    id = id,
                    password = password
                )
            )
        } returns DefaultLogResult.failure(Throwable())

        joinViewModel.id.value = id.value
        joinViewModel.password.value = password.value

        // when
        joinViewModel.join()

        // then
        assertEquals(JoinState.Failed(), joinViewModel.state.value)
    }

    companion object {

        private const val VALID_ID = "jinuk99"
        private const val VALID_PASSWORD = "s7730857!"

        private const val INVALID_ID = "abc"
        private const val INVALID_PASSWORD = "123"

        private const val EXAMPLE_ID = "qwer7772"
        private const val EXAMPLE_PASSWORD = "qwer7772!"

        private const val DUPLICATED_ID = "jinuk99"
    }
}
