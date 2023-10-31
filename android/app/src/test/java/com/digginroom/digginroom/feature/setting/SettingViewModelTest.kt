package com.digginroom.digginroom.feature.setting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.digginroom.digginroom.fixture.LogResult
import com.digginroom.digginroom.repository.TokenRepository
import io.mockk.coEvery
import io.mockk.coVerify
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
class SettingViewModelTest {

    private lateinit var settingViewModel: SettingViewModel
    private lateinit var tokenRepository: TokenRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        tokenRepository = mockk()
        settingViewModel = SettingViewModel(tokenRepository)
    }

    @Test
    fun `로그아웃을 진행하기 위한 상태가 된다`() {
        // when
        settingViewModel.startLogout()

        val actual = settingViewModel.uiState.value

        // then
        val expected = SettingUiState.Logout.InProgress

        assertEquals(expected, actual)
    }

    @Test
    fun `로그아웃을 하면 로컬에 저장되어 있는 토큰을 삭제한다`() {
        // when
        settingViewModel.logout()

        // then
        coVerify { tokenRepository.delete() }
    }

    @Test
    fun `저장되어있던 토큰 값 삭제에 성공하면 로그아웃 완료 상태가 된다`() {
        // given
        coEvery {
            tokenRepository.delete()
        } returns LogResult.success(Unit)

        // when
        settingViewModel.logout()

        val actual = settingViewModel.uiState.value

        // then
        val expected = SettingUiState.Logout.Done

        assertEquals(expected, actual)
    }

    @Test
    fun `설정 취소 상태가 된다`() {
        // when
        settingViewModel.cancel()

        val actual = settingViewModel.uiState.value

        // then
        val expected = SettingUiState.Cancel

        assertEquals(expected, actual)
    }
}
