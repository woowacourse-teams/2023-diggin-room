package com.digginroom.digginroom.feature.genretaste

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.model.GenreTasteModel
import com.digginroom.digginroom.model.GenreTasteSelectionModel
import com.digginroom.digginroom.model.mapper.GenreTasteMapper.toDomain
import com.digginroom.digginroom.model.mapper.GenreTasteMapper.toModel
import com.digginroom.digginroom.model.room.genre.Genre
import com.digginroom.digginroom.model.room.genre.GenreTaste
import com.digginroom.digginroom.model.room.genre.GenresTaste
import com.digginroom.digginroom.repository.GenreTasteRepository
import com.dygames.di.annotation.NotCaching
import kotlinx.coroutines.launch

@NotCaching
class GenreTasteViewModel @Keep constructor(
    private val genreTasteRepository: GenreTasteRepository
) : ViewModel() {

    private val genresTaste = GenresTaste(
        Genre.values().map { GenreTaste(it, false) }
    )

    private val _uiState: MutableLiveData<GenreTasteUiState> = MutableLiveData(
        GenreTasteUiState.InProgress(
            genreTasteSelection = GenreTasteSelectionModel(
                genresTaste = genresTaste.value.map { it.toModel() },
                onSelect = ::switchSelection
            )
        )
    )
    val uiState: LiveData<GenreTasteUiState>
        get() = _uiState

    fun switchSelection(genreTasteModel: GenreTasteModel) {
        genresTaste.switchSelection(genreTasteModel.toDomain())

        _uiState.value = GenreTasteUiState.InProgress(
            genreTasteSelection = GenreTasteSelectionModel(
                genresTaste = genresTaste.value.map { it.toModel() },
                onSelect = ::switchSelection
            )
        )
    }

    fun endSurvey() {
        viewModelScope.launch {
            genreTasteRepository.post(genresTaste.selected)
                .onSuccess {
                    _uiState.value = GenreTasteUiState.Succeed
                }.onFailure {
                    _uiState.value = GenreTasteUiState.Failed
                }
        }
    }
}
