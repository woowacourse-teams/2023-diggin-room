package com.digginroom.digginroom.feature.room.comment

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.feature.room.comment.uistate.CommentResponseUiState
import com.digginroom.digginroom.feature.room.comment.uistate.CommentSubmitUiState
import com.digginroom.digginroom.feature.room.comment.uistate.CommentUiState
import com.digginroom.digginroom.feature.room.comment.uistate.SubmitState
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

    private val _commentResponseUiState: MutableLiveData<CommentResponseUiState> = MutableLiveData(
        CommentResponseUiState.Succeed.Find()
    )

    private val _commentSubmitUiState: MutableLiveData<CommentSubmitUiState> = MutableLiveData(
        CommentSubmitUiState(
            delete = ::deleteComment,
            changeToUpdate = ::changeToUpdateMode,
            state = SubmitState.POST
        )
    )
    private var lastCommentId: Long? = null
    private var isLastComment: Boolean = false

    val commentResponseUiState: LiveData<CommentResponseUiState> get() = _commentResponseUiState
    val commentSubmitUiState: LiveData<CommentSubmitUiState> get() = _commentSubmitUiState

    fun findComments(roomId: Long, size: Int) {
        println("findComments $comments")
        if (shouldSkipFindComments()) return
        _commentResponseUiState.value = CommentResponseUiState.Loading
        viewModelScope.launch {
            commentRepository.findComments(
                roomId = roomId,
                lastCommentId = lastCommentId,
                size = size
            ).onSuccess { newComments ->
                onFindCommentsSuccess(newComments)
            }.onFailure {
                _commentResponseUiState.value = CommentResponseUiState.Failed.Find
            }
        }
    }

    private fun shouldSkipFindComments(): Boolean {
        return isLastComment || commentResponseUiState.value is CommentResponseUiState.Loading
    }

    private fun onFindCommentsSuccess(newComments: List<Comment>) {
        isLastComment = newComments.isEmpty()
        refreshComments(newComments)
    }

    private fun refreshComments(newComments: List<Comment>) {
        comments = comments + newComments
        _commentResponseUiState.value = if (isLastComment) {
            CommentResponseUiState.Succeed.Find(comments.map { it.toModel() })
        } else {
            lastCommentId = comments.last().id
            CommentResponseUiState.Succeed.Find(comments.map { it.toModel() } + CommentUiState.Loading)
        }
    }

    fun submitComment(
        roomId: Long,
        comment: String,
        commentToUpdate: CommentUiState.CommentModel?
    ) {
        if (_commentResponseUiState.value == CommentResponseUiState.Loading) return
        _commentResponseUiState.value = CommentResponseUiState.Loading

        when (_commentSubmitUiState.value?.state ?: return) {
            SubmitState.POST -> postComment(roomId, comment)
            SubmitState.UPDATE -> commentToUpdate?.let {
                updateComment(
                    roomId,
                    comment,
                    it.id
                )
            }
        }
    }

    private fun postComment(roomId: Long, comment: String) {
        viewModelScope.launch {
            commentRepository.postComment(roomId, comment).onSuccess { comment ->
                onPostCommentsSuccess(comment)
            }.onFailure {
                _commentResponseUiState.value = CommentResponseUiState.Failed.Submit
            }
        }
    }

    private fun onPostCommentsSuccess(comment: Comment) {
        comments = comments.toMutableList().apply { add(0, comment) }
        _commentResponseUiState.value =
            CommentResponseUiState.Succeed.Submit(comments.map { it.toModel() })
    }

    private fun updateComment(roomId: Long, comment: String, commentId: Long) {
        viewModelScope.launch {
            commentRepository.updateComment(roomId, commentId, comment).onSuccess {
                onUpdateCommentSuccess(commentId, comment)
            }.onFailure {
                _commentResponseUiState.value = CommentResponseUiState.Failed.Submit
            }
        }
    }

    private fun onUpdateCommentSuccess(commentId: Long, comment: String) {
        comments = comments.toMutableList().apply {
            val index = indexOfFirst { it.id == commentId }
            this[index] = this[index].copy(comment = comment)
        }
        _commentResponseUiState.value =
            CommentResponseUiState.Succeed.Submit(comments.map { it.toModel() })
        changeToPostMode()
    }

    fun deleteComment(roomId: Long, commentId: Long) {
        if (_commentResponseUiState.value == CommentResponseUiState.Loading) return
        _commentResponseUiState.value = CommentResponseUiState.Loading

        viewModelScope.launch {
            commentRepository.deleteComment(roomId, commentId).onSuccess {
                onDeleteCommentSuccess(commentId)
            }.onFailure {
                _commentResponseUiState.value = CommentResponseUiState.Failed.Delete
            }
        }
    }

    private fun onDeleteCommentSuccess(commentId: Long) {
        comments = comments.filter { it.id != commentId }
        _commentResponseUiState.value =
            CommentResponseUiState.Succeed.Delete(comments.map { it.toModel() })
    }

    private fun changeToUpdateMode() {
        _commentSubmitUiState.value = _commentSubmitUiState.value?.copy(
            state = SubmitState.UPDATE
        )
    }

    private fun changeToPostMode() {
        _commentSubmitUiState.value = _commentSubmitUiState.value?.copy(
            state = SubmitState.POST
        )
    }
}
