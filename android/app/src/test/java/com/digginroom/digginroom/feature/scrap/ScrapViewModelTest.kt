package com.digginroom.digginroom.feature.scrap

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.digginroom.digginroom.feature.scrap.viewmodel.ScrapUiState
import com.digginroom.digginroom.feature.scrap.viewmodel.ScrapViewModel
import com.digginroom.digginroom.fixture.LogResult
import com.digginroom.digginroom.fixture.RoomFixture.SELECTED_ROOM_POSITION
import com.digginroom.digginroom.fixture.RoomFixture.ScrappedRooms
import com.digginroom.digginroom.model.mapper.RoomMapper.toModel
import com.digginroom.digginroom.model.room.scrap.playlist.Playlist
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
import org.junit.Assert.assertTrue
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
            roomRepository = roomRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `스크랩된 룸들에 대한 목록을 받아온다`() {
        // given
        val rooms = ScrappedRooms()

        coEvery {
            roomRepository.findScrapped()
        } returns LogResult.success(rooms)

        // when
        scrapViewModel.findScrappedRooms()

        // then
        val expected = rooms.map { it.toModel() }
        assertEquals(expected, scrapViewModel.uiState.value?.rooms)
    }

    @Test
    fun `스크랩된 룸들을 받아오면 기본 상태가 된다`() {
        // given
        coEvery {
            roomRepository.findScrapped()
        } returns LogResult.success(ScrappedRooms())

        // when
        scrapViewModel.findScrappedRooms()

        val actual = scrapViewModel.uiState.value

        // then
        val expected = actual is ScrapUiState.Default

        assertTrue(expected)
    }

    @Test
    fun `기본 상태에서 선택 이벤트는 스크랩 룸 목록에 있는 룸을 탐색하는 상태로 변경한다`() {
        // given
        coEvery {
            roomRepository.findScrapped()
        } returns LogResult.success(ScrappedRooms())

        scrapViewModel.findScrappedRooms()

        // when
        scrapViewModel.uiState.value?.onSelect?.invoke(SELECTED_ROOM_POSITION)

        val actual = scrapViewModel.uiState.value

        // then
        val expected = actual is ScrapUiState.Navigation

        assertTrue(expected)
    }

    @Test
    fun `룸을 탐색하는 상태에서의 선택 이벤트는 룸을 탐색하는 상태를 동일하게 유지한다`() {
        // given
        coEvery {
            roomRepository.findScrapped()
        } returns LogResult.success(ScrappedRooms())

        scrapViewModel.findScrappedRooms()
        scrapViewModel.uiState.value?.onSelect?.invoke(SELECTED_ROOM_POSITION)

        // when
        scrapViewModel.uiState.value?.onSelect?.invoke(SELECTED_ROOM_POSITION)

        val actual = scrapViewModel.uiState.value

        // then
        val expected = actual is ScrapUiState.Navigation

        assertTrue(expected)
    }

    @Test
    fun `룸들을 선택하기 위한 상태로 변경한다`() {
        // given

        // when
        scrapViewModel.startRoomSelection()

        val actual = scrapViewModel.uiState.value

        // then
        val expected = actual is ScrapUiState.Selection

        assertTrue(expected)
    }

    @Test
    fun `룸들을 선택하기 위한 상태에서의 선택 이벤트는 추출하기 위한 상태를 동일하게 유지한다`() {
        // given
        coEvery {
            roomRepository.findScrapped()
        } returns LogResult.success(ScrappedRooms())

        scrapViewModel.findScrappedRooms()
        scrapViewModel.startRoomSelection()

        // when
        scrapViewModel.uiState.value?.onSelect?.invoke(SELECTED_ROOM_POSITION)

        val actual = scrapViewModel.uiState.value

        // then
        val expected = actual is ScrapUiState.Selection

        assertTrue(expected)
    }

    @Test
    fun `플레이리스트를 추출하기 위한 상태로 변경한다`() {
        // given

        // when
        scrapViewModel.startPlaylistExtraction()

        val actual = scrapViewModel.uiState.value

        // then
        val expected = actual is ScrapUiState.PlaylistExtraction

        assertTrue(expected)
    }

    @Test
    fun `플레이리스트를 추출 성공 시 기본 상태로 변경된다`() {
        // given
        coEvery {
            roomRepository.postPlaylist(
                authCode = "authCode",
                playlist = Playlist(listOf())
            )
        } returns LogResult.success(Unit)

        // when
        scrapViewModel.extractPlaylist("authCode")

        val actual = scrapViewModel.uiState.value

        // then
        val expected = actual is ScrapUiState.Default

        assertTrue(expected)
    }
}
