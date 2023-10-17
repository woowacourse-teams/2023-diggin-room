package com.digginroom.digginroom.feature.room.comment

import android.util.Log
import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.feature.room.comment.uistate.CommentResponseUiState
import com.digginroom.digginroom.feature.room.comment.uistate.CommentSubmitUiState
import com.digginroom.digginroom.feature.room.comment.uistate.SubmitState
import com.digginroom.digginroom.model.CommentModel
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
        CommentResponseUiState.Succeed()
    )

    private val _commentSubmitUiState: MutableLiveData<CommentSubmitUiState> = MutableLiveData(
        CommentSubmitUiState(
            delete = ::deleteComment,
            changeToUpdate = ::changeToUpdateMode,
            state = SubmitState.POST
        )
    )
    private var lastCommentId: Long = 0
    var isLastRoom: Boolean = false
    private val TAG = "hkhk"

    val commentResponseUiState: LiveData<CommentResponseUiState> get() = _commentResponseUiState
    val commentSubmitUiState: LiveData<CommentSubmitUiState> get() = _commentSubmitUiState

    fun findComments(roomId: Long) {
        Log.d(TAG, "findComments: ")
        if (_commentResponseUiState.value == CommentResponseUiState.Loading) return
        _commentResponseUiState.value = CommentResponseUiState.Loading

        viewModelScope.launch {
            if (comments.isEmpty()) {
                commentRepository.findComments(roomId = roomId, size = COMMENT_LIMIT_COUNT)
            } else {
                commentRepository.findComments(roomId, lastCommentId, COMMENT_LIMIT_COUNT)
            }.onSuccess { newComments ->
                if (newComments.isEmpty()) isLastRoom = true
                comments = newComments.sortedByDescending { it.createdAt }
                if (newComments.isNotEmpty()) lastCommentId = comments.last().id
                _commentResponseUiState.value =
                    CommentResponseUiState.Succeed(comments.map { it.toModel() })
            }.onFailure {
                _commentResponseUiState.value = CommentResponseUiState.Failed(FIND_COMMENT_FAILED)
            }
        }
    }

    fun submitComment(roomId: Long, comment: String, updateTargetCommentModel: CommentModel?) {
        if (_commentResponseUiState.value == CommentResponseUiState.Loading) return
        _commentResponseUiState.value = CommentResponseUiState.Loading

        when (_commentSubmitUiState.value?.state ?: return) {
            SubmitState.POST -> postComment(roomId, comment)
            SubmitState.UPDATE -> updateTargetCommentModel?.let {
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
                comments = comments.toMutableList().apply { add(0, comment) }
                _commentResponseUiState.value =
                    CommentResponseUiState.Succeed(comments.map { it.toModel() })
            }.onFailure {
                _commentResponseUiState.value = CommentResponseUiState.Failed(POST_COMMENT_FAILED)
            }
        }
    }

    private fun updateComment(roomId: Long, comment: String, commentId: Long) {
        viewModelScope.launch {
            commentRepository.updateComment(roomId, commentId, comment).onSuccess {
                comments = comments.toMutableList().apply {
                    val index = indexOfFirst { it.id == commentId }
                    this[index] = this[index].copy(comment = comment)
                }
                println(comments)
                _commentResponseUiState.value =
                    CommentResponseUiState.Succeed(comments.map { it.toModel() })
                changeToPostMode()
            }.onFailure {
                _commentResponseUiState.value = CommentResponseUiState.Failed(UPDATE_COMMENT_FAILED)
            }
        }
    }

    fun deleteComment(roomId: Long, commentId: Long) {
        if (_commentResponseUiState.value == CommentResponseUiState.Loading) return
        _commentResponseUiState.value = CommentResponseUiState.Loading

        viewModelScope.launch {
            commentRepository.deleteComment(roomId, commentId).onSuccess {
                comments = comments.filter { it.id != commentId }
                _commentResponseUiState.value =
                    CommentResponseUiState.Succeed(comments.map { it.toModel() })
            }.onFailure {
                _commentResponseUiState.value = CommentResponseUiState.Failed(DELETE_COMMENT_FAILED)
            }
        }
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

    companion object {
        const val FIND_COMMENT_FAILED = "댓글을 불러올 수 없습니다."
        const val POST_COMMENT_FAILED = "댓글 작성에 실패하였습니다."
        const val UPDATE_COMMENT_FAILED = "댓글 수정에 실패하였습니다."
        const val DELETE_COMMENT_FAILED = "댓글 삭제에 실패하였습니다."

        const val COMMENT_LIMIT_COUNT = 10
    }
}
