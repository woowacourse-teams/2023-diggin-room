package com.digginroom.digginroom.feature.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.digginroom.digginroom.fixture.AccountFixture.EXAMPLE_ID
import com.digginroom.digginroom.fixture.AccountFixture.EXAMPLE_PASSWORD
import com.digginroom.digginroom.fixture.AccountFixture.ID_TOKEN
import com.digginroom.digginroom.fixture.LogResult
import com.digginroom.digginroom.model.AccountModel
import com.digginroom.digginroom.model.user.Member
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
class LoginViewModelTest {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var accountRepository: AccountRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        accountRepository = mockk(relaxed = true)

        loginViewModel = LoginViewModel(accountRepository = accountRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `로그인 실패시 로그인 실패 상태가 된다`() {
        // given
        coEvery {
            accountRepository.postLogIn(
                id = EXAMPLE_ID,
                password = EXAMPLE_PASSWORD
            )
        } returns LogResult.failure()

        // when
        loginViewModel.login(
            AccountModel(
                id = EXAMPLE_ID,
                password = EXAMPLE_PASSWORD
            )
        )

        // then
        assertEquals(LoginUiState.Failed, loginViewModel.uiState.getValue())
    }

    @Test
    fun `로그인 성공시 취향 설문 조사를 이미 한 경우 취향 조사를 끝마친 로그인 성공 상태가 된다`() {
        // given
        coEvery {
            accountRepository.postLogIn(
                id = EXAMPLE_ID,
                password = EXAMPLE_PASSWORD
            )
        } returns LogResult.success(Member(true))

        // when
        loginViewModel.login(
            AccountModel(
                id = EXAMPLE_ID,
                password = EXAMPLE_PASSWORD
            )
        )

        // then
        assertEquals(LoginUiState.Succeed.Surveyed, loginViewModel.uiState.getValue())
    }

    @Test
    fun `로그인 성공시 취향 설문 조사를 하지 않은 경우 취향 조사를 하지 않은 로그인 성공 상태가 된다`() {
        // given
        coEvery {
            accountRepository.postLogIn(
                id = EXAMPLE_ID,
                password = EXAMPLE_PASSWORD
            )
        } returns LogResult.success(Member(false))

        // when
        loginViewModel.login(
            AccountModel(
                id = EXAMPLE_ID,
                password = EXAMPLE_PASSWORD
            )
        )

        // then
        assertEquals(LoginUiState.Succeed.NotSurveyed, loginViewModel.uiState.getValue())
    }

    @Test
    fun `구글 로그인 진행 상태가 된다`() {
        // when
        loginViewModel.startGoogleLogin()
        val actual = loginViewModel.uiState.getValue()

        // then
        val expected = LoginUiState.InProgress.GoogleLogin
        assertEquals(expected, actual)
    }

    @Test
    fun `id 토큰을 이용한 구글 로그인 실패시 로그인 실패 상태가 된다`() {
        // given
        coEvery {
            accountRepository.postLogin(ID_TOKEN)
        } returns LogResult.failure()

        // when
        loginViewModel.googleLogin(ID_TOKEN)

        // then
        assertEquals(LoginUiState.Failed, loginViewModel.uiState.getValue())
    }

    @Test
    fun `id 토큰을 이용한 구글 로그인 성공시 로그인 성공 상태가 된다`() {
        // given
        coEvery {
            accountRepository.postLogin(ID_TOKEN)
        } returns LogResult.success(Member(true))

        // when
        loginViewModel.googleLogin(ID_TOKEN)

        // then
        assertEquals(LoginUiState.Succeed.Surveyed, loginViewModel.uiState.getValue())
    }

    @Test
    fun `카카오 로그인 진행 상태가 된다`() {
        // when
        loginViewModel.startKakaoLogin()
        val actual = loginViewModel.uiState.getValue()

        // then
        val expected = LoginUiState.InProgress.KaKaoLogin
        assertEquals(expected, actual)
    }
}
