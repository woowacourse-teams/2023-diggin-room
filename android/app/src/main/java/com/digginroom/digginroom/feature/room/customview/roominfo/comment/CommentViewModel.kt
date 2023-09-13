package com.digginroom.digginroom.feature.room.customview.roominfo.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.model.comment.Comment
import com.digginroom.digginroom.model.mapper.CommentMapper.toModel
import com.digginroom.digginroom.repository.CommentRepository
import kotlinx.coroutines.launch

class CommentViewModel(
    private val commentRepository: CommentRepository,
    private var comments: List<Comment> = emptyList()
) : ViewModel() {

    private val _commentState: MutableLiveData<CommentState> = MutableLiveData(CommentState.Loading)
    val commentState: LiveData<CommentState> get() = _commentState

    fun findComments(roomId: Long) {
        if (_commentState.value == CommentState.Loading) return
        _commentState.value = CommentState.Loading
        viewModelScope.launch {
            commentRepository.findComments(roomId).onSuccess { newComments ->
                comments = newComments
                _commentState.value = CommentState.Succeed(comments.map { it.toModel() })
            }.onFailure {
                _commentState.value = CommentState.Failed(FIND_COMMENT_FAILED)
            }
        }
    }

    fun postComment(roomId: Long, comment: String) {
        if (_commentState.value == CommentState.Loading) return
        _commentState.value = CommentState.Loading
        viewModelScope.launch {
            commentRepository.postComment(roomId, comment).onSuccess { comment ->
                comments = comments + comment
                _commentState.value = CommentState.Succeed(comments.map { it.toModel() })
            }.onFailure {
                _commentState.value = CommentState.Failed(POST_COMMENT_FAILED)
            }
        }
    }

    fun updateComment(roomId: Long, commentId: Long, comment: String) {
        if (_commentState.value == CommentState.Loading) return
        _commentState.value = CommentState.Loading
        viewModelScope.launch {
            commentRepository.updateComment(roomId, commentId, comment).onSuccess {
                comments = comments.toMutableList().apply {
                    val index = indexOfFirst { it.id == commentId }
                    this[index] = this[index].copy(comment = comment)
                }
                _commentState.value = CommentState.Succeed(comments.map { it.toModel() })
            }.onFailure {
                _commentState.value = CommentState.Failed(UPDATE_COMMENT_FAILED)
            }
        }
    }

    fun deleteComment(roomId: Long, commentId: Long) {
        if (_commentState.value == CommentState.Loading) return
        _commentState.value = CommentState.Loading
        viewModelScope.launch {
            commentRepository.deleteComment(roomId, commentId).onSuccess {
                comments = comments.filter { it.id != commentId }
                _commentState.value = CommentState.Succeed(comments.map { it.toModel() })
            }.onFailure {
                _commentState.value = CommentState.Failed(DELETE_COMMENT_FAILED)
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
