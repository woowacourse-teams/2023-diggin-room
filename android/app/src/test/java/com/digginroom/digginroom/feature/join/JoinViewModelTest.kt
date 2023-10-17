package com.digginroom.digginroom.feature.join

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.digginroom.digginroom.fixture.AccountFixture.DUPLICATED_ID
import com.digginroom.digginroom.fixture.AccountFixture.EXAMPLE_ID
import com.digginroom.digginroom.fixture.AccountFixture.INVALID_ID
import com.digginroom.digginroom.fixture.AccountFixture.INVALID_PASSWORD
import com.digginroom.digginroom.fixture.AccountFixture.VALID_ID
import com.digginroom.digginroom.fixture.AccountFixture.VALID_PASSWORD
import com.digginroom.digginroom.fixture.LogResult
import com.digginroom.digginroom.model.JoinAccountModel
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
        accountRepository = mockk(relaxed = true)
        joinViewModel = JoinViewModel(accountRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `아이디 유효성 검증 성공 시 유효한 아이디이다`() {
        // given
        val id = VALID_ID

        // when
        joinViewModel.validateId(id)
        val uiState = joinViewModel
            .uiState
            .value as? JoinUiState.InProgress
        val actual = uiState?.joinVerification?.isValidId

        // then
        val expected = true

        assertEquals(expected, actual)
    }

    @Test
    fun `아이디 유효성 검증 실패 시 유효하지 않은 아이디이다`() {
        // given
        val id = INVALID_ID

        // when
        joinViewModel.validateId(id)
        val uiState = joinViewModel
            .uiState
            .value as? JoinUiState.InProgress
        val actual = uiState?.joinVerification?.isValidId

        // then
        val expected = false
        assertEquals(expected, actual)
    }

    @Test
    fun `아이디 유효성 검증 실패 시 회원가입이 가능하지 않은 상태이다`() {
        // given
        val id = INVALID_ID

        // when
        joinViewModel.validateId(id)
        val uiState = joinViewModel
            .uiState
            .value as? JoinUiState.InProgress
        val actual = uiState?.joinVerification?.isJoinAble

        // then
        val expected = false
        assertEquals(expected, actual)
    }

    @Test
    fun `아이디 중복 검사를 한 경우에 중복 검사를 한 아이디이다`() {
        // given
        val id = Id(EXAMPLE_ID)

        coEvery {
            accountRepository.fetchIsDuplicatedId(id)
        } returns LogResult.success(true)

        // when
        joinViewModel.validateIdDuplication(id.value)
        val uiState = joinViewModel
            .uiState
            .value as? JoinUiState.InProgress
        val actual = uiState?.joinVerification?.isCheckedIdDuplication

        // then
        val expected = true

        assertEquals(expected, actual)
    }

    @Test
    fun `아이디 중복된 경우 중복된 아이디 상태이다`() {
        // given
        val id = Id(DUPLICATED_ID)

        coEvery {
            accountRepository.fetchIsDuplicatedId(id)
        } returns LogResult.success(true)

        // when
        joinViewModel.validateIdDuplication(id.value)
        val uiState = joinViewModel
            .uiState
            .value as? JoinUiState.InProgress
        val actual = uiState?.joinVerification?.isDuplicatedId

        // then
        val expected = true

        assertEquals(expected, actual)
    }

    @Test
    fun `중복된 아이디인 경우에 회원가입이 가능하지 않은 상태이다`() {
        // given
        val id = Id(DUPLICATED_ID)

        coEvery {
            accountRepository.fetchIsDuplicatedId(id)
        } returns LogResult.success(true)

        // when
        joinViewModel.validateIdDuplication(id.value)
        val uiState = joinViewModel
            .uiState
            .value as? JoinUiState.InProgress
        val actual = uiState?.joinVerification?.isJoinAble

        // then
        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun `비밀번호 유효성 검증 실패 시 유효하지 않은 비밀번호이다`() {
        // given
        val password = INVALID_PASSWORD

        // when
        joinViewModel.validatePassword(password, "")
        val uiState = joinViewModel
            .uiState
            .value as? JoinUiState.InProgress
        val actual = uiState?.joinVerification?.isValidPassword

        // then
        val expected = false

        assertEquals(expected, actual)
    }

    //
    @Test
    fun `비밀번호 유효성 검증 성공 시 유효한 비밀번호이다`() {
        // given
        val password = VALID_PASSWORD

        // when
        joinViewModel.validatePassword(password, "")
        val uiState = joinViewModel
            .uiState
            .value as? JoinUiState.InProgress
        val actual = uiState?.joinVerification?.isValidPassword

        // then
        val expected = true

        assertEquals(expected, actual)
    }

    @Test
    fun `비밀번호 유효성 검증 실패 시 회원가입이 가능하지 않은 상태이다`() {
        // given
        val password = INVALID_PASSWORD

        // when
        joinViewModel.validatePassword(password, "")
        val uiState = joinViewModel
            .uiState
            .value as? JoinUiState.InProgress
        val actual = uiState?.joinVerification?.isValidPassword

        // then
        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun `재입력한 비밀번호와 기존에 입력한 비밀번호가 일치하지 않은 경우 비밀번호 동일하지 않은 비밀번호이다`() {
        // given
        val password = VALID_PASSWORD
        val reInputPassword = INVALID_PASSWORD

        // when
        joinViewModel.validatePasswordEquality(password, reInputPassword)
        val uiState = joinViewModel
            .uiState
            .value as? JoinUiState.InProgress
        val actual = uiState?.joinVerification?.isEqualReInputPassword

        // then
        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun `재입력한 비밀번호와 기존에 입력한 비밀번호가 일치하지 않는 경우 회원가입이 가능하지 않은 상태이다`() {
        // given
        val password = VALID_PASSWORD
        val reInputPassword = INVALID_PASSWORD

        // when
        joinViewModel.validatePasswordEquality(password, reInputPassword)
        val uiState = joinViewModel
            .uiState
            .value as? JoinUiState.InProgress
        val actual = uiState?.joinVerification?.isJoinAble

        // then
        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun `회원가입 성공시 회원가입 성공 상태가 된다`() {
        // given
        val id = VALID_ID
        val password = VALID_PASSWORD
        val reInputPassword = VALID_PASSWORD

        coEvery {
            accountRepository.postJoin(
                Account(
                    id = Id(id),
                    password = Password(password)
                )
            )
        } returns LogResult.success(Unit)

        // when
        joinViewModel.join(
            JoinAccountModel(
                id = id,
                password = password,
                reInputPassword = reInputPassword
            )
        )
        val actual = joinViewModel.uiState.value

        // then
        val expected = JoinUiState.Succeed
        assertEquals(expected, actual)
    }

    @Test
    fun `회원가입 실패시 회원가입 실패 상태가 된다`() {
        // given
        val id = VALID_ID
        val password = VALID_PASSWORD
        val reInputPassword = VALID_PASSWORD

        coEvery {
            accountRepository.postJoin(
                Account(
                    id = Id(id),
                    password = Password(password)
                )
            )
        } returns LogResult.failure()

        // when
        joinViewModel.join(
            JoinAccountModel(
                id = id,
                password = password,
                reInputPassword = reInputPassword
            )
        )
        val actual = joinViewModel.uiState.value

        // then
        val expected = JoinUiState.Failed()
        assertEquals(expected, actual)
    }

    @Test
    fun `회원가입 취소시 회원가입 취소 상태가 된다`() {
        // given

        // when
        joinViewModel.cancel()
        val actual = joinViewModel.uiState.value

        // then
        val expected = JoinUiState.Cancel

        assertEquals(expected, actual)
    }
}
