package com.digginroom.digginroom

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.digginroom.digginroom.feature.login.LoginState
import com.digginroom.digginroom.feature.login.LoginViewModel
import com.digginroom.digginroom.logging.DefaultLogResult
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
    fun `로그인 실패시 로그인 상태를 나타내는 변수를 failed로 바꾼다`() {
        // given
        coEvery {
            accountRepository.postLogIn(
                id = EXAMPLE_ID,
                password = EXAMPLE_PASSWORD
            )
        } returns DefaultLogResult.failure(Throwable())

        loginViewModel.id.value = EXAMPLE_ID
        loginViewModel.password.value = EXAMPLE_PASSWORD

        // when
        loginViewModel.login()

        // then
        assertEquals(LoginState.FAILED, loginViewModel.state.value)
    }

    @Test
    fun `로그인 성공시 로그인 상태를 나타내는 변수를 sucess로 바꾼다`() {
        // given
        coEvery {
            accountRepository.postLogIn(
                id = EXAMPLE_ID,
                password = EXAMPLE_PASSWORD
            )
        } returns DefaultLogResult.success(TOKEN)

        loginViewModel.id.value = EXAMPLE_ID
        loginViewModel.password.value = EXAMPLE_PASSWORD

        // when
        loginViewModel.login()

        // then
        assertEquals(LoginState.SUCCEED, loginViewModel.state.value)
    }

    @Test
    fun `로그인 성공시 로그인 상태를 나타내는 token값을 토큰 저장소에 저장한다`() {
        // given
        coEvery {
            accountRepository.postLogIn(
                id = EXAMPLE_ID,
                password = EXAMPLE_PASSWORD
            )
        } returns DefaultLogResult.success(TOKEN)

        loginViewModel.id.value = EXAMPLE_ID
        loginViewModel.password.value = EXAMPLE_PASSWORD

        // when
        loginViewModel.login()

        // then
        coVerify { tokenRepository.save(TOKEN) }
    }

    companion object {

        private const val EXAMPLE_ID = "digginroom"
        private const val EXAMPLE_PASSWORD = "digginroom"
        private const val TOKEN = ""
    }
}
