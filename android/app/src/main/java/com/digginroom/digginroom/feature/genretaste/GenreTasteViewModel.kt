package com.digginroom.digginroom.feature.genretaste

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.model.GenreTasteModel
import com.digginroom.digginroom.model.mapper.GenreTasteMapper.toDomain
import com.digginroom.digginroom.model.mapper.GenreTasteMapper.toModel
import com.digginroom.digginroom.model.room.genre.Genre
import com.digginroom.digginroom.model.room.genre.GenreTaste
import com.digginroom.digginroom.model.room.genre.GenresTaste
import com.digginroom.digginroom.repository.GenreTasteRepository
import kotlinx.coroutines.launch

class GenreTasteViewModel(
    private val genreTasteRepository: GenreTasteRepository
) : ViewModel() {

    private val genresTaste = GenresTaste(
        Genre.values().map { GenreTaste(it, false) }
    )

    private val _genres: MutableLiveData<List<GenreTasteModel>> = MutableLiveData(
        genresTaste.value.map { it.toModel() }
    )
    val genres: LiveData<List<GenreTasteModel>>
        get() = _genres

    private val _state: MutableLiveData<GenreTasteSurveyState> =
        MutableLiveData(GenreTasteSurveyState.RUNNING)
    val state: LiveData<GenreTasteSurveyState>
        get() = _state

    fun switchSelection(genreTasteModel: GenreTasteModel) {
        genresTaste.switchSelection(genreTasteModel.toDomain())

        _genres.value = genresTaste.value.map { it.toModel() }
    }

    fun endSurvey() {
        viewModelScope.launch {
            genreTasteRepository.post(
                genresTaste.selected
            ).onSuccess {
                _state.value = GenreTasteSurveyState.SUCCEED
            }.onFailure {
                _state.value = GenreTasteSurveyState.FAILED
            }
        }
    }
}
