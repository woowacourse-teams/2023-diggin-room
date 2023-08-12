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
import com.digginroom.digginroom.repository.MemberRepository
import kotlinx.coroutines.launch

class GenreTasteViewModel(
    private val memberRepository: MemberRepository
) : ViewModel() {

    private val genresTaste = GenresTaste(
        Genre.values().map { GenreTaste(it, false) }
    )

    private val _genres: MutableLiveData<List<GenreTasteModel>> = MutableLiveData(
        genresTaste.value.map { it.toModel() }
    )
    val genres: LiveData<List<GenreTasteModel>>
        get() = _genres

    fun switchSelection(genreTasteModel: GenreTasteModel) {
        genresTaste.switchSelection(genreTasteModel.toDomain())

        _genres.value = genresTaste.value.map { it.toModel() }
    }

    fun endSurvey() {
        viewModelScope.launch {
            memberRepository.postGenresTaste(
                genresTaste.selected
            )
        }
    }
}
