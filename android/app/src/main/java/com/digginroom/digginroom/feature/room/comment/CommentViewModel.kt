package com.digginroom.digginroom.feature.room.comment

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.feature.room.comment.uistate.CommentUiState
import com.digginroom.digginroom.feature.room.comment.uistate.state.CommentState
import com.digginroom.digginroom.model.comment.Comment
import com.digginroom.digginroom.model.mapper.CommentMapper.toModel
import com.digginroom.digginroom.repository.CommentRepository
import com.dygames.di.annotation.NotCaching
import kotlinx.coroutines.launch

@NotCaching
class CommentViewModel @Keep constructor(
    private val commentRepository: CommentRepository
) : ViewModel() {
    private var comments: List<Comment> = emptyList()
    private val _commentState: MutableLiveData<CommentUiState> =
        MutableLiveData(CommentUiState(CommentState.Succeed(comments.map { it.toModel() })))
    val commentState: LiveData<CommentUiState> get() = _commentState

    fun findComments(roomId: Long) {
        if (_commentState.value?.state == CommentState.Loading) return
        _commentState.value = _commentState.value?.copy(state = CommentState.Loading)
        viewModelScope.launch {
            commentRepository.findComments(roomId).onSuccess { newComments ->
                comments = newComments.sortedByDescending { it.createdAt }
                _commentState.value =
                    _commentState.value?.copy(state = CommentState.Succeed(comments.map { it.toModel() }))
            }.onFailure {
                _commentState.value =
                    _commentState.value?.copy(state = CommentState.Failed(FIND_COMMENT_FAILED))
            }
        }
    }

    fun postComment(roomId: Long, comment: String) {
        if (_commentState.value?.state == CommentState.Loading) return
        _commentState.value = _commentState.value?.copy(state = CommentState.Loading)
        viewModelScope.launch {
            commentRepository.postComment(roomId, comment).onSuccess { comment ->
                comments = comments.toMutableList().apply { add(0, comment) }
                _commentState.value =
                    _commentState.value?.copy(state = CommentState.Succeed(comments.map { it.toModel() }))
            }.onFailure {
                _commentState.value =
                    _commentState.value?.copy(state = CommentState.Failed(POST_COMMENT_FAILED))
            }
        }
    }

    fun updateComment(roomId: Long, commentId: Long, comment: String) {
        if (_commentState.value?.state == CommentState.Loading) return
        _commentState.value = _commentState.value?.copy(state = CommentState.Loading)
        viewModelScope.launch {
            commentRepository.updateComment(roomId, commentId, comment).onSuccess {
                comments = comments.toMutableList().apply {
                    val index = indexOfFirst { it.id == commentId }
                    this[index] = this[index].copy(comment = comment)
                }
                _commentState.value =
                    _commentState.value?.copy(state = CommentState.Succeed(comments.map { it.toModel() }))
            }.onFailure {
                _commentState.value =
                    _commentState.value?.copy(state = CommentState.Failed(UPDATE_COMMENT_FAILED))
            }
        }
    }

    fun deleteComment(roomId: Long, commentId: Long) {
        if (_commentState.value?.state == CommentState.Loading) return
        _commentState.value = _commentState.value?.copy(state = CommentState.Loading)
        viewModelScope.launch {
            commentRepository.deleteComment(roomId, commentId).onSuccess {
                comments = comments.filter { it.id != commentId }
                _commentState.value =
                    _commentState.value?.copy(state = CommentState.Succeed(comments.map { it.toModel() }))
            }.onFailure {
                _commentState.value =
                    _commentState.value?.copy(state = CommentState.Failed(DELETE_COMMENT_FAILED))
            }
        }
    }

    companion object {
        const val FIND_COMMENT_FAILED = "댓글을 불러올 수 없습니다."
        const val POST_COMMENT_FAILED = "댓글 작성에 실패하였습니다."
        const val UPDATE_COMMENT_FAILED = "댓글 수정에 실패하였습니다."
        const val DELETE_COMMENT_FAILED = "댓글 삭제에 실패하였습니다."
    }
}
