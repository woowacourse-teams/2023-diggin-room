package com.digginroom.digginroom.feature.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.model.CommentModel
import com.digginroom.digginroom.model.mapper.CommentMapper.toModel
import com.digginroom.digginroom.repository.CommentRepository
import kotlinx.coroutines.launch

class CommentViewModel(
    private val commentRepository: CommentRepository
) : ViewModel() {

    private val _comments: MutableLiveData<List<CommentModel>> = MutableLiveData()
    val comments: LiveData<List<CommentModel>>
        get() = _comments

    fun findComments(roomId: Long) {
        viewModelScope.launch {
            commentRepository.findComments(roomId).onSuccess { comments ->
                _comments.value = comments.map { it.toModel() }
            }.onFailure {}
        }
    }

    fun postComment(roomId: Long, comment: String) {
        viewModelScope.launch {
            commentRepository.postComment(roomId, comment).onSuccess {}.onFailure {}
        }
    }
}
