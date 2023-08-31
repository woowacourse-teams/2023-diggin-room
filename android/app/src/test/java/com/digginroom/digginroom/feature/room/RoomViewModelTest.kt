package com.digginroom.digginroom.feature.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.digginroom.digginroom.feature.room.customview.roomplayer.RoomState
import com.digginroom.digginroom.fixture.LogResult
import com.digginroom.digginroom.fixture.RoomFixture.Room
import com.digginroom.digginroom.model.mapper.RoomMapper.toDomain
import com.digginroom.digginroom.repository.RoomRepository
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
        roomViewModel.findNext()

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
        roomViewModel.findNext()

        // then
        assert(roomViewModel.cachedRoom.value is RoomState.Error)
    }

    @Test
    fun `스크랩 요청을 보내면 해당 룸이 스크랩 된다`() {
        // given
        val defaultRoom = Room(isScrapped = false)
        val roomId = 0L
        val expectedIsScrapped = true

        coEvery {
            roomRepository.findNext()
        } returns LogResult.success(defaultRoom)

        coEvery {
            roomRepository.postScrapById(roomId)
        } returns LogResult.success(Unit)

        // when
        roomViewModel.findNext()
        roomViewModel.postScrap(roomId)

        val actual = (roomViewModel.cachedRoom.value as RoomState.Success).rooms[0].isScrapped

        // then
        coVerify { roomRepository.postScrapById(roomId) }
        assertEquals(
            expectedIsScrapped,
            actual
        )
    }

    @Test
    fun `스크랩 취소 요청을 보내면 해당 룸이 스크랩 취소 된다`() {
        // given
        val defaultRoom = Room(isScrapped = true)
        val roomId = 0L
        val expectedIsScrapped = false

        coEvery {
            roomRepository.findNext()
        } returns LogResult.success(defaultRoom)

        coEvery {
            roomRepository.removeScrapById(roomId)
        } returns LogResult.success(Unit)

        // when
        roomViewModel.findNext()
        roomViewModel.removeScrap(roomId)

        val actual = (roomViewModel.cachedRoom.value as RoomState.Success).rooms[0].isScrapped

        // then
        coVerify { roomRepository.removeScrapById(roomId) }
        assertEquals(
            expectedIsScrapped,
            actual
        )
    }
}
