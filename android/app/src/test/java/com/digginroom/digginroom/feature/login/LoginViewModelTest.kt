package com.digginroom.digginroom.feature.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.digginroom.digginroom.fixture.AccountFixture.EXAMPLE_ID
import com.digginroom.digginroom.fixture.AccountFixture.EXAMPLE_PASSWORD
import com.digginroom.digginroom.fixture.AccountFixture.ID_TOKEN
import com.digginroom.digginroom.fixture.AccountFixture.TOKEN
import com.digginroom.digginroom.fixture.LogResult
import com.digginroom.digginroom.model.user.Member
import com.digginroom.digginroom.repository.AccountRepository
import com.digginroom.digginroom.repository.TokenRepository
import io.mockk.coEvery
import io.mockk.coVerify
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
    private lateinit var tokenRepository: TokenRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        accountRepository = mockk(relaxed = true)
        tokenRepository = mockk(relaxed = true)

        loginViewModel = LoginViewModel(
            accountRepository = accountRepository,
            tokenRepository = tokenRepository
        )
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

        loginViewModel.id.value = EXAMPLE_ID
        loginViewModel.password.value = EXAMPLE_PASSWORD

        // when
        loginViewModel.login()

        // then
        assertEquals(LoginState.Failed, loginViewModel.state.value)
    }

    @Test
    fun `로그인 성공시 취향 설문 조사를 이미 한 경우 취향 조사를 끝마친 로그인 성공 상태가 된다`() {
        // given
        coEvery {
            accountRepository.postLogIn(
                id = EXAMPLE_ID,
                password = EXAMPLE_PASSWORD
            )
        } returns LogResult.success(Member(true, TOKEN))

        loginViewModel.id.value = EXAMPLE_ID
        loginViewModel.password.value = EXAMPLE_PASSWORD

        // when
        loginViewModel.login()

        // then
        assertEquals(LoginState.Succeed.Surveyed, loginViewModel.state.value)
    }

    @Test
    fun `로그인 성공시 취향 설문 조사를 하지 않은 경우 취향 조사를 하지 않은 로그인 성공 상태가 된다`() {
        // given
        coEvery {
            accountRepository.postLogIn(
                id = EXAMPLE_ID,
                password = EXAMPLE_PASSWORD
            )
        } returns LogResult.success(Member(false, TOKEN))

        loginViewModel.id.value = EXAMPLE_ID
        loginViewModel.password.value = EXAMPLE_PASSWORD

        // when
        loginViewModel.login()

        // then
        assertEquals(LoginState.Succeed.NotSurveyed, loginViewModel.state.value)
    }

    @Test
    fun `로그인 성공시 토큰 값을 저장한다`() {
        // given
        coEvery {
            accountRepository.postLogIn(
                id = EXAMPLE_ID,
                password = EXAMPLE_PASSWORD
            )
        } returns LogResult.success(Member(true, TOKEN))

        loginViewModel.id.value = EXAMPLE_ID
        loginViewModel.password.value = EXAMPLE_PASSWORD

        // when
        loginViewModel.login()

        // then
        coVerify { tokenRepository.save(TOKEN) }
    }

    @Test
    fun `id 토큰을 이용한 로그인 실패시 로그인 실패 상태가 된다`() {
        // given
        coEvery {
            accountRepository.postLogin(ID_TOKEN)
        } returns LogResult.failure()

        // when
        loginViewModel.login(ID_TOKEN)

        // then
        assertEquals(LoginState.Failed, loginViewModel.state.value)
    }

    @Test
    fun `id 토큰을 이용한 로그인 성공시 로그인 성공 상태가 된다`() {
        // given
        coEvery {
            accountRepository.postLogin(ID_TOKEN)
        } returns LogResult.success(Member(true, TOKEN))

        // when
        loginViewModel.login(ID_TOKEN)

        // then
        assertEquals(LoginState.Succeed.Surveyed, loginViewModel.state.value)
    }

    @Test
    fun `id 토큰을 이용한 로그인 성공시 서버로 부터 받아온 토큰 값을 저장한다`() {
        // given
        coEvery {
            accountRepository.postLogin(ID_TOKEN)
        } returns LogResult.success(Member(true, TOKEN))

        // when
        loginViewModel.login(ID_TOKEN)

        // then
        coVerify { tokenRepository.save(TOKEN) }
    }
}
