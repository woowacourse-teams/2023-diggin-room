package com.digginroom.digginroom.feature.tutorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.repository.TutorialRepository
import kotlinx.coroutines.launch

class TutorialViewModel(private val tutorialRepository: TutorialRepository) : ViewModel() {
    private val _tutorialCompleted: MutableLiveData<TutorialUiState> = MutableLiveData()
    val tutorialCompleted: LiveData<TutorialUiState> get() = _tutorialCompleted

    fun completeTutorial() {
        _tutorialCompleted.value = TutorialUiState.Loading
        viewModelScope.launch {
            tutorialRepository.save(true).onSuccess {
                _tutorialCompleted.value = TutorialUiState.Success(true)
            }.onFailure { _tutorialCompleted.value = TutorialUiState.Error(it) }
        }
    }
}