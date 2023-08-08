package com.digginroom.digginroom.feature.scraproom

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.digginroom.digginroom.feature.scrap.ScrapRoomViewModel
import com.digginroom.digginroom.repository.RoomRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ScrapRoomViewModelTest {

    private lateinit var roomRepository: RoomRepository
    private lateinit var scrapRoomViewModel: ScrapRoomViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        roomRepository = mockk(relaxed = true)
        scrapRoomViewModel = ScrapRoomViewModel(
            rooms = mutableListOf(),
            roomRepository = roomRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `룸 스크랩 요청을 보낸다`() {
        // given
        val roomId: Long = 10

        // when
        scrapRoomViewModel.postScrap(roomId)

        // then
        coVerify { roomRepository.postScrapById(roomId) }
    }

    @Test
    fun `룸 스크랩 취소 요청을 보낸다`() {
        // given
        val roomId: Long = 10

        // when
        scrapRoomViewModel.removeScrap(roomId)

        // then
        coVerify { roomRepository.removeScrapById(roomId) }
    }
}
