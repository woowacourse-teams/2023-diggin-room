package com.digginroom.digginroom.feature.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.digginroom.digginroom.feature.login.LoginUiState
import com.digginroom.digginroom.fixture.AccountFixture.TOKEN
import com.digginroom.digginroom.fixture.LogResult
import com.digginroom.digginroom.model.user.Member
import com.digginroom.digginroom.repository.MemberRepository
import com.digginroom.digginroom.repository.TokenRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    private lateinit var splashViewModel: SplashViewModel
    private lateinit var tokenRepository: TokenRepository
    private lateinit var memberRepository: MemberRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        tokenRepository = mockk(relaxed = true)
        memberRepository = mockk(relaxed = true)
        splashViewModel = SplashViewModel(
            tokenRepository = tokenRepository,
            memberRepository = memberRepository
        )
    }

    @Test
    fun `토큰을 받아오는 것을 실패했으면 로그인 실패 상태가 된다`() {
        // given
        coEvery {
            tokenRepository.fetch()
        } returns LogResult.failure()

        // when
        splashViewModel.validateToken()

        // then
        val expected = LoginUiState.Failed.AutoLogin
        val actual = splashViewModel.loginUiState.value

        assertEquals(expected, actual)
    }

    @Test
    fun `사용자 정보를 조회했을때 취향조사를 하지 않은 상태라면 취향조사를 하지 않은 로그인 상태가 된다`() {
        // given
        coEvery {
            tokenRepository.fetch()
        } returns LogResult.success(TOKEN)

        coEvery {
            memberRepository.fetch(TOKEN)
        } returns LogResult.success(Member(false))

        // when
        splashViewModel.validateToken()

        // then
        val expected = LoginUiState.Succeed.NotSurveyed
        val actual = splashViewModel.loginUiState.value

        assertEquals(expected, actual)
    }

    @Test
    fun `사용자 정보를 조회했을때 취향조사에 한 상태라면 취향조사를 한 로그인 상태가 된다`() {
        // given
        coEvery {
            tokenRepository.fetch()
        } returns LogResult.success(TOKEN)

        coEvery {
            memberRepository.fetch(TOKEN)
        } returns LogResult.success(Member(true))

        // when
        splashViewModel.validateToken()

        // then
        val expected = LoginUiState.Succeed.Surveyed
        val actual = splashViewModel.loginUiState.value

        assertEquals(expected, actual)
    }
}
