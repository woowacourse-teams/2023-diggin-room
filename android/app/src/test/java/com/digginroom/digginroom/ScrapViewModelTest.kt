package com.digginroom.digginroom

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.digginroom.digginroom.feature.scrap.ScrapViewModel
import com.digginroom.digginroom.model.mapper.RoomMapper.toModel
import com.digginroom.digginroom.repository.RoomRepository
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
class ScrapViewModelTest {

    private lateinit var scrapViewModel: ScrapViewModel
    private lateinit var roomRepository: RoomRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        roomRepository = mockk()
        scrapViewModel = ScrapViewModel(
            rooms = mutableListOf(),
            roomRepository = roomRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `뷰모델이 생성될 때 스크랩된 룸들에 대한 목록을 받아온다`() {
        // given
        val rooms = RoomFixture.createRooms()

        coEvery {
            roomRepository.findScrapped()
        } returns Result.success(rooms)

        // when
        val viewModel = ScrapViewModel(
            rooms = mutableListOf(),
            roomRepository = roomRepository
        )
        val expected = rooms.map { it.toModel() }

        // then
        assertEquals(expected, viewModel.scrappedRooms.value)
    }
}
