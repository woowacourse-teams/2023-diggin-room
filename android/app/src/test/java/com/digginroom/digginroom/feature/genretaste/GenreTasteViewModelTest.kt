package com.digginroom.digginroom.feature.genretaste

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.digginroom.digginroom.fixture.LogResult
import com.digginroom.digginroom.model.mapper.GenreTasteMapper.toDomain
import com.digginroom.digginroom.model.mapper.GenreTasteMapper.toModel
import com.digginroom.digginroom.model.room.genre.Genre
import com.digginroom.digginroom.model.room.genre.GenreTaste
import com.digginroom.digginroom.repository.GenreTasteRepository
import com.digginroom.digginroom.repository.TutorialRepository
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
class GenreTasteViewModelTest {

    private lateinit var genreTasteViewModel: GenreTasteViewModel
    private lateinit var genreTasteRepository: GenreTasteRepository
    private lateinit var tutorialRepository: TutorialRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        genreTasteRepository = mockk(relaxed = true)
        tutorialRepository = mockk(relaxed = true)
        genreTasteViewModel = GenreTasteViewModel(genreTasteRepository, tutorialRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `선택되지 않은 음악장르를 선택하면 해당 음악장르는 선택된 상태가 된다`() {
        // given
        val genreTaste = GenreTaste(
            genre = Genre.SOUNDS_AND_EFFECTS,
            isSelected = false
        )

        // when
        genreTasteViewModel.switchSelection(genreTaste.toModel())
        val uiState = genreTasteViewModel.uiState.value as? GenreTasteUiState.InProgress
        val actual = uiState?.genreTasteSelection
            ?.genresTaste
            ?.find { it.title == genreTaste.genre.title }
            ?.toDomain()

        // then
        val expected = GenreTaste(
            genre = Genre.SOUNDS_AND_EFFECTS,
            isSelected = true
        )

        assertEquals(expected, actual)
    }

    @Test
    fun `선택된 음악장르를 다시 선택하면 해당 음악장르는 선택되지 않은 상태가 된다`() {
        // given
        val genreTaste = GenreTaste(
            genre = Genre.SOUNDS_AND_EFFECTS,
            isSelected = true
        )

        // when
        genreTasteViewModel.switchSelection(genreTaste.toModel())
        val uiState = genreTasteViewModel.uiState.value as? GenreTasteUiState.InProgress
        val actual = uiState?.genreTasteSelection
            ?.genresTaste
            ?.find { it.title == genreTaste.genre.title }
            ?.toDomain()

        // then
        val expected = GenreTaste(
            genre = Genre.SOUNDS_AND_EFFECTS,
            isSelected = false
        )

        assertEquals(expected, actual)
    }

    @Test
    fun `음악 장르에 대한 취향 조사가 끝나고 성공적인 응답을 받은 경우 성공 상태가 된다`() {
        // given
        val genreTaste = GenreTaste(Genre.SOUNDS_AND_EFFECTS, false).toModel()

        genreTasteViewModel.switchSelection(genreTaste)

        coEvery {
            genreTasteRepository.post(listOf(Genre.SOUNDS_AND_EFFECTS))
        } returns LogResult.success(Unit)

        // when
        genreTasteViewModel.endSurvey()
        val actual = genreTasteViewModel.uiState.value

        // then
        assertTrue(actual is GenreTasteUiState.Succeed)
    }

    @Test
    fun `음악 장르에 대한 취향 조사가 끝나고 성공적인 응답을 받지 못한 경우 실패 상태가 된다`() {
        // given
        val genreTaste = GenreTaste(Genre.SOUNDS_AND_EFFECTS, false).toModel()

        genreTasteViewModel.switchSelection(genreTaste)

        coEvery {
            genreTasteRepository.post(listOf(Genre.SOUNDS_AND_EFFECTS))
        } returns LogResult.failure()

        // when
        genreTasteViewModel.endSurvey()
        val actual = genreTasteViewModel.uiState.value

        // then
        assertTrue(actual is GenreTasteUiState.Failed)
    }
}
