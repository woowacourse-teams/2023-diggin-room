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

    private val _commentActionState: MutableLiveData<CommentActionState> =
        MutableLiveData(CommentActionState.Post)
    val commentActionState: LiveData<CommentActionState> get() = _commentActionState

    fun findComments(roomId: Long) {
        writtenComment.value = ""
        viewModelScope.launch {
            commentRepository.findComments(roomId).onSuccess { comments ->
                _comments.value = comments.map { it.toModel() }
            }.onFailure {}
        }
    }

    fun postComment(roomId: Long, comment: String) {
        viewModelScope.launch {
            commentRepository.postComment(roomId, comment).onSuccess {
                _comments.value = _comments.value + it.toModel()
            }.onFailure {
            }
        }
    }

    fun updateWrittenComment(roomId: Long, commentId: Long, comment: String, updatePosition: Int) {
        viewModelScope.launch {
            commentRepository.updateComment(roomId, commentId, comment).onSuccess {
                _comments.value = _comments.value.toMutableList().apply {
                    this[updatePosition] = this[updatePosition].copy(comment = comment)
                }
                finishUpdatingComment()
            }.onFailure {
                finishUpdatingComment()
            }
        }
    }

    fun deleteComment(roomId: Long, commentId: Long, deletePosition: Int) {
        viewModelScope.launch {
            commentRepository.deleteComment(roomId, commentId).onSuccess {
                _comments.value =
                    _comments.value.filterIndexed { index, _ -> index != deletePosition }
            }.onFailure {
            }
        }
    }

    fun startUpdatingComment() {
        _commentActionState.value = CommentActionState.Update
    }

    private fun finishUpdatingComment() {
        _commentActionState.value = CommentActionState.Post
    }

    fun updateWrittenComment(comment: String) {
        this.writtenComment.value = comment
    }

    fun updateCommentState(commentState: CommentActionState) {
        _commentActionState.value = commentState
    }
}
