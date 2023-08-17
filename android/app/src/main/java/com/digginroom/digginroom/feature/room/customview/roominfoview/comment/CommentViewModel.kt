package com.digginroom.digginroom.feature.room.customview.roominfoview.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.livedata.NonNullMutableLiveData
import com.digginroom.digginroom.model.CommentModel
import com.digginroom.digginroom.model.mapper.CommentMapper.toModel
import com.digginroom.digginroom.repository.CommentRepository
import kotlinx.coroutines.launch

class CommentViewModel(
    private val commentRepository: CommentRepository
) : ViewModel() {

    private val _comments: NonNullMutableLiveData<List<CommentModel>> = NonNullMutableLiveData(
        emptyList()
    )
    val comments: LiveData<List<CommentModel>>
        get() = _comments

    val writtenComment: NonNullMutableLiveData<String> = NonNullMutableLiveData("")

    private val _commentState: MutableLiveData<CommentState> = MutableLiveData()
    val commentState: LiveData<CommentState> get() = _commentState

    fun findComments(roomId: Long) {
        writtenComment.value = ""
        _commentState.value = CommentState.Post.Ready
        viewModelScope.launch {
            commentRepository.findComments(roomId).onSuccess { comments ->
                _comments.value = comments.map { it.toModel() }
            }.onFailure {}
        }
    }

    fun postComment(roomId: Long, comment: String) {
        if (_commentState.value == CommentState.Post.Loading) return
        _commentState.value = CommentState.Post.Loading

        viewModelScope.launch {
            commentRepository.postComment(roomId, comment).onSuccess {
                _comments.value = _comments.value + it.toModel()
                _commentState.value = CommentState.Post.Succeed
            }.onFailure {
                _commentState.value = CommentState.Post.Failed
            }
        }
    }

    fun updateWrittenComment(roomId: Long, commentId: Long, comment: String, updatePosition: Int) {
        if (_commentState.value == CommentState.Update.Loading) return
        _commentState.value = CommentState.Update.Loading

        viewModelScope.launch {
            commentRepository.updateComment(roomId, commentId, comment).onSuccess {
                _comments.value = _comments.value.toMutableList().apply {
                    this[updatePosition] = this[updatePosition].copy(comment = comment)
                }
                _commentState.value = CommentState.Update.Succeed
            }.onFailure {
                _commentState.value = CommentState.Update.Failed
            }
        }
    }

    fun deleteComment(roomId: Long, commentId: Long, deletePosition: Int) {
        if (_commentState.value == CommentState.Delete.Loading) return
        _commentState.value = CommentState.Delete.Loading

        viewModelScope.launch {
            commentRepository.deleteComment(roomId, commentId).onSuccess {
                _comments.value =
                    _comments.value.filterIndexed { index, _ -> index != deletePosition }
                _commentState.value = CommentState.Delete.Succeed
            }.onFailure {
                _commentState.value = CommentState.Delete.Failed
            }
        }
    }

    fun updateWrittenComment(comment: String) {
        this.writtenComment.value = comment
    }

    fun updateCommentState(commentState: CommentState) {
        _commentState.value = commentState
    }
}
