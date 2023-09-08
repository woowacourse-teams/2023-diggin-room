package com.digginroom.digginroom.feature.room.customview.roominfoview.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.livedata.NonNullMutableLiveData
import com.digginroom.digginroom.model.CommentModel
import com.digginroom.digginroom.model.mapper.CommentMapper.toModel
import com.digginroom.digginroom.repository.CommentRepository
import com.digginroom.digginroom.util.SingleLiveEvent
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
    private val _commentRequestState: SingleLiveEvent<CommentRequestState> =
        SingleLiveEvent()
    val commentRequestState: LiveData<CommentRequestState> get() = _commentRequestState

    fun findComments(roomId: Long) {
        writtenComment.value = ""
        viewModelScope.launch {
            commentRepository.findComments(roomId).onSuccess { comments ->
                _comments.value = comments.map { it.toModel() }
            }.onFailure {
                _commentRequestState.value = CommentRequestState.Failed(FIND_COMMENT_FAILED)
            }
        }
    }

    fun postComment(roomId: Long, comment: String) {
        if (commentRequestState.value == CommentRequestState.Loading) return
        _commentRequestState.value = CommentRequestState.Loading
        viewModelScope.launch {
            commentRepository.postComment(roomId, comment).onSuccess {
                _comments.value = _comments.value + it.toModel()
                _commentRequestState.value = CommentRequestState.Done
            }.onFailure {
                _commentRequestState.value = CommentRequestState.Failed(POST_COMMENT_FAILED)
            }
        }
    }

    fun updateWrittenComment(roomId: Long, commentId: Long, comment: String, updatePosition: Int) {
        if (commentRequestState.value == CommentRequestState.Loading) return
        _commentRequestState.value = CommentRequestState.Loading
        viewModelScope.launch {
            commentRepository.updateComment(roomId, commentId, comment).onSuccess {
                _comments.value = _comments.value.toMutableList().apply {
                    this[updatePosition] = this[updatePosition].copy(comment = comment)
                }
                _commentActionState.value = CommentActionState.Post
            }.onFailure {
                _commentActionState.value = CommentActionState.Post
                _commentRequestState.value = CommentRequestState.Failed(UPDATE_COMMENT_FAILED)
            }
        }
    }

    fun deleteComment(roomId: Long, commentId: Long, deletePosition: Int) {
        viewModelScope.launch {
            commentRepository.deleteComment(roomId, commentId).onSuccess {
                _comments.value =
                    _comments.value.filterIndexed { index, _ -> index != deletePosition }
            }.onFailure {
                _commentRequestState.value = CommentRequestState.Failed(DELETE_COMMENT_FAILED)
            }
        }
    }

    fun startUpdatingComment() {
        _commentActionState.value = CommentActionState.Update
    }

    fun updateWrittenComment(comment: String) {
        this.writtenComment.value = comment
    }

    fun updateCommentState(commentState: CommentActionState) {
        _commentActionState.value = commentState
    }

    fun handleCommentRequestState() {
        _commentRequestState.call()
    }

    companion object {
        const val FIND_COMMENT_FAILED = "댓글을 불러올 수 없습니다."
        const val POST_COMMENT_FAILED = "댓글 작성에 실패하였습니다."
        const val UPDATE_COMMENT_FAILED = "댓글 수정에 실패하였습니다."
        const val DELETE_COMMENT_FAILED = "댓글 삭제에 실패하였습니다."
    }
}
