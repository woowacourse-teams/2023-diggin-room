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

    private val _comments: MutableLiveData<List<CommentModel>> = MutableLiveData()
    val comments: LiveData<List<CommentModel>>
        get() = _comments

    val writtenComment: NonNullMutableLiveData<String> = NonNullMutableLiveData("")

    private val _commentState: MutableLiveData<CommentState> = MutableLiveData()
    val commentState: LiveData<CommentState> get() = _commentState

    fun findComments(roomId: Long) {
        writtenComment.value = ""
        _commentState.value = CommentState.Create.Ready
        viewModelScope.launch {
            commentRepository.findComments(roomId).onSuccess { comments ->
                _comments.value = comments.map { it.toModel() }
            }.onFailure {}
        }
    }

    fun postComment(roomId: Long, comment: String) {
        if (_commentState.value == CommentState.Create.Loading) {
            return
        }
        _commentState.value = CommentState.Create.Loading

        viewModelScope.launch {
            commentRepository.postComment(roomId, comment).onSuccess {
                val oldComments: MutableList<CommentModel> =
                    _comments.value?.toMutableList() ?: mutableListOf()
                oldComments.add(it.toModel())
                _comments.value = oldComments
                _commentState.value = CommentState.Create.Succeed
            }.onFailure {
                _commentState.value = CommentState.Create.Failed
            }
        }
    }

    fun updateWrittenComment(roomId: Long, commentId: Long, comment: String, updatePosition: Int) {
        if (_commentState.value == CommentState.Edit.Loading) {
            return
        }
        _commentState.value = CommentState.Edit.Loading

        viewModelScope.launch {
            commentRepository.updateComment(roomId, commentId, comment).onSuccess {
                val oldComments: MutableList<CommentModel> =
                    _comments.value?.toMutableList() ?: mutableListOf()
                oldComments[updatePosition] =
                    oldComments[updatePosition].copy(comment = comment)
                _comments.value = oldComments
                _commentState.value = CommentState.Edit.Succeed
            }.onFailure {
                _commentState.value = CommentState.Edit.Failed
            }
        }
    }

    fun deleteComment(roomId: Long, commentId: Long, deletePosition: Int) {
        if (_commentState.value == CommentState.Delete.Loading) {
            return
        }
        _commentState.value = CommentState.Delete.Loading

        viewModelScope.launch {
            commentRepository.deleteComment(roomId, commentId).onSuccess {
                val oldComments: MutableList<CommentModel> =
                    _comments.value?.toMutableList() ?: mutableListOf()
                oldComments.removeAt(deletePosition)
                _comments.value = oldComments
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
