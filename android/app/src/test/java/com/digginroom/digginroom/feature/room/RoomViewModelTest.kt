package com.digginroom.digginroom.feature.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.digginroom.digginroom.feature.room.customview.roomplayer.RoomState
import com.digginroom.digginroom.fixture.LogResult
import com.digginroom.digginroom.fixture.RoomFixture.Room
import com.digginroom.digginroom.model.mapper.RoomMapper.toDomain
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
class RoomViewModelTest {

    private lateinit var roomViewModel: RoomViewModel
    private lateinit var roomRepository: RoomRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        roomRepository = mockk()
        roomViewModel = RoomViewModel(mutableListOf(), roomRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `새로운 룸을 요청하면 성공 상태와 함께 룸을 받아온다`() {
        // given
        val actual = Room()
        coEvery {
            roomRepository.findNext()
        } returns LogResult.success(actual)

        // when
        roomViewModel.findNextRoom()

        // then
        val expected = Room()

        assert(roomViewModel.cachedRoom.value is RoomState.Success)
        assertEquals(
            expected,
            (roomViewModel.cachedRoom.value as RoomState.Success).rooms[0].toDomain()
        )
    }

    @Test
    fun `새로운 룸을 요청할때 실패하면 실패 상태를 받아온다`() {
        // given
        coEvery {
            roomRepository.findNext()
        } returns LogResult.failure()

        // when
        roomViewModel.findNextRoom()

        // then
        assert(roomViewModel.cachedRoom.value is RoomState.Error)
    }
}
