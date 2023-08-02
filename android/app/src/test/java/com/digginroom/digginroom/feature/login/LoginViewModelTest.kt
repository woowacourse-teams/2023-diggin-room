package com.digginroom.digginroom.feature.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.digginroom.digginroom.fixture.AccountFixture.EXAMPLE_ID
import com.digginroom.digginroom.fixture.AccountFixture.EXAMPLE_PASSWORD
import com.digginroom.digginroom.fixture.AccountFixture.TOKEN
import com.digginroom.digginroom.fixture.LogResult
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
        assertEquals(LoginState.FAILED, loginViewModel.state.value)
    }

    @Test
    fun `로그인 성공시 로그인 성공 상태이다`() {
        // given
        coEvery {
            accountRepository.postLogIn(
                id = EXAMPLE_ID,
                password = EXAMPLE_PASSWORD
            )
        } returns LogResult.success(TOKEN)

        loginViewModel.id.value = EXAMPLE_ID
        loginViewModel.password.value = EXAMPLE_PASSWORD

        // when
        loginViewModel.login()

        // then
        assertEquals(LoginState.SUCCEED, loginViewModel.state.value)
    }

    @Test
    fun `로그인 성공시 토큰 값을 저장한다`() {
        // given
        coEvery {
            accountRepository.postLogIn(
                id = EXAMPLE_ID,
                password = EXAMPLE_PASSWORD
            )
        } returns LogResult.success(TOKEN)

        loginViewModel.id.value = EXAMPLE_ID
        loginViewModel.password.value = EXAMPLE_PASSWORD

        // when
        loginViewModel.login()

        // then
        coVerify { tokenRepository.save(TOKEN) }
    }
}
