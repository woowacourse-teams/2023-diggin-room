package com.digginroom.digginroom.feature.genretaste

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.feature.tutorial.TutorialState
import com.digginroom.digginroom.model.GenreTasteModel
import com.digginroom.digginroom.model.GenreTasteSelectionModel
import com.digginroom.digginroom.model.mapper.GenreTasteMapper.toDomain
import com.digginroom.digginroom.model.mapper.GenreTasteMapper.toModel
import com.digginroom.digginroom.model.room.genre.Genre
import com.digginroom.digginroom.model.room.genre.GenreTaste
import com.digginroom.digginroom.model.room.genre.GenresTaste
import com.digginroom.digginroom.repository.GenreTasteRepository
import com.digginroom.digginroom.repository.TutorialRepository
import kotlinx.coroutines.launch

class GenreTasteViewModel(
    private val genreTasteRepository: GenreTasteRepository,
    private val tutorialRepository: TutorialRepository
) : ViewModel() {

    private val genresTaste = GenresTaste(Genre.values().map { GenreTaste(it, false) })

    private val _uiState: MutableLiveData<GenreTasteUiState> = MutableLiveData(
        GenreTasteUiState.InProgress(
            genreTasteSelection = GenreTasteSelectionModel(
                genresTaste = genresTaste.value.map { it.toModel() }, onSelect = ::switchSelection
            )
        )
    )
    val uiState: LiveData<GenreTasteUiState>
        get() = _uiState
    private val _tutorialState: MutableLiveData<TutorialState> = MutableLiveData()

    fun switchSelection(genreTasteModel: GenreTasteModel) {
        genresTaste.switchSelection(genreTasteModel.toDomain())

        _uiState.value = GenreTasteUiState.InProgress(
            genreTasteSelection = GenreTasteSelectionModel(
                genresTaste = genresTaste.value.map { it.toModel() }, onSelect = ::switchSelection
            )
        )
    }

    fun endSurvey() {
        viewModelScope.launch {
            genreTasteRepository.post(genresTaste.selected).onSuccess {
                _uiState.value = GenreTasteUiState.Succeed
            }.onFailure {
                _uiState.value = GenreTasteUiState.Failed
            }
        }
    }

    fun startTutorial() {
        viewModelScope.launch {
            tutorialRepository.save(false).onSuccess {
                _tutorialState.value = TutorialState.Success(false)
            }.onFailure { _tutorialState.value = TutorialState.Error(it) }
        }
    }
}
