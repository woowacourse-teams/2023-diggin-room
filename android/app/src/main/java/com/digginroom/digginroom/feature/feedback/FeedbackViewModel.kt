package com.digginroom.digginroom.feature.feedback

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.model.FeedbackModel
import com.digginroom.digginroom.model.mapper.FeedbackMapper.toDomain
import com.digginroom.digginroom.repository.FeedbackRepository
import com.dygames.di.annotation.NotCaching
import kotlinx.coroutines.launch

@NotCaching
class FeedbackViewModel @Keep constructor(
    private val feedbackRepository: FeedbackRepository
) : ViewModel() {

    private val _state: MutableLiveData<FeedbackUiState> = MutableLiveData()
    val state: LiveData<FeedbackUiState>
        get() = _state

    fun updateFeedback(feedback: FeedbackModel) {
        _state.value = FeedbackUiState.FeedbackUi.Loading
        viewModelScope.launch {
            feedbackRepository.postFeedback(feedback.toDomain()).onSuccess {
                _state.value = FeedbackUiState.FeedbackUi.Succeed
            }.onFailure {
                _state.value = FeedbackUiState.FeedbackUi.Failed
            }
        }
    }

    fun cancel() {
        _state.value = FeedbackUiState.Cancel
    }
}
