package com.digginroom.digginroom.feature.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digginroom.digginroom.feature.room.customview.PostCommentState
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

    val comment: NonNullMutableLiveData<String> = NonNullMutableLiveData("")

    private val _postCommentState: MutableLiveData<PostCommentState> =
        MutableLiveData(PostCommentState.READY)
    val postCommentState: LiveData<PostCommentState> get() = _postCommentState

    fun findComments(roomId: Long) {
        viewModelScope.launch {
            commentRepository.findComments(roomId).onSuccess { comments ->
                _comments.value = comments.map { it.toModel() }
            }.onFailure {}
        }
    }

    fun postComment(roomId: Long, comment: String) {
        viewModelScope.launch {
            commentRepository.postComment(roomId, comment).onSuccess {
                val oldComments: List<CommentModel> = _comments.value ?: listOf()
                _comments.value = oldComments + it.toModel()
                _postCommentState.value = PostCommentState.SUCCEED
            }.onFailure {
                _postCommentState.value = PostCommentState.FAILED
            }
        }
    }
}
