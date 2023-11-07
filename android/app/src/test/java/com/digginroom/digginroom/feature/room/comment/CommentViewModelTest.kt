package com.digginroom.digginroom.feature.room.comment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.digginroom.digginroom.feature.room.comment.uistate.CommentResponseUiState
import com.digginroom.digginroom.fixture.CommentFixture.Comment
import com.digginroom.digginroom.fixture.CommentFixture.Comments
import com.digginroom.digginroom.fixture.LogResult
import com.digginroom.digginroom.model.CommentItem
import com.digginroom.digginroom.model.mapper.CommentMapper.toModel
import com.digginroom.digginroom.repository.CommentRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CommentViewModelTest {
    private lateinit var commentViewModel: CommentViewModel
    private lateinit var commentRepository: CommentRepository
    private val comments = Comments()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        commentRepository = mockk()
        commentViewModel = CommentViewModel(commentRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `해당 룸의 댓글 조회 성공 시 commentResponseUiState 에 받아온 댓글이 담긴 조회 성공 상태로 바뀐다`() {
        // given
        val roomId: Long = 0

        coEvery {
            commentRepository.findComments(any(), any(), any())
        } returns LogResult.success(comments)

        // when
        commentViewModel.findComments(roomId, 10)

        // then
        val actual =
            commentViewModel.commentResponseUiState.value as CommentResponseUiState.Succeed.Find
        val expected = comments.map { it.toModel() } + CommentItem.Loading
        assertEquals(expected, actual.comments)
    }

    @Test
    fun `해당 룸의 댓글 조회 실패 시 commentResponseUiState 가 조회 실패 상태로 바뀐다`() {
        // given
        val roomId: Long = 0

        coEvery {
            commentRepository.findComments(any(), any(), any())
        } returns LogResult.failure()

        // when
        commentViewModel.findComments(roomId, 10)

        // then
        val actual = commentViewModel.commentResponseUiState.value
        val expected = CommentResponseUiState.Failed.Find
        assertEquals(expected, actual)
    }

    @Test
    fun `댓글 작성 성공 시 commentResponseUiState 가 작성한 댓글이 포함된 전송 성공 상태로 바뀐다`() {
        // given
        val comment = Comment(id = 3)
        val roomId: Long = 0

        coEvery {
            commentRepository.postComment(any(), any())
        } returns LogResult.success(comment)

        // when
        commentViewModel.submitComment(roomId, comment.comment, null)

        // then
        val actual =
            commentViewModel.commentResponseUiState.value as CommentResponseUiState.Succeed.Submit
        val expected = listOf(comment.toModel())
        assertEquals(expected, actual.comments)
    }

    @Test
    fun `댓글 작성 실패 시 commentResponseUiState 가 전송 실패 상태로 바뀐다`() {
        // given
        val comment = Comment(id = 3)
        val roomId: Long = 0

        coEvery {
            commentRepository.postComment(any(), any())
        } returns LogResult.failure()

        // when
        commentViewModel.submitComment(roomId, comment.comment, null)

        // then
        val actual = commentViewModel.commentResponseUiState.value
        val expected = CommentResponseUiState.Failed.Submit
        assertEquals(expected, actual)
    }

    @Test
    fun `댓글 삭제 성공 시 commentResponseUiState 가 삭제한 댓글이 제거된 삭제 성공 상태로 바뀐다`() {
        // given
        val roomId: Long = 0
        val commentId: Long = 3
        val comment = Comment(id = commentId)

        coEvery {
            commentRepository.postComment(any(), any())
        } returns LogResult.success(comment)

        coEvery {
            commentRepository.deleteComment(any(), any())
        } returns LogResult.success(Unit)

        // when
        commentViewModel.submitComment(roomId, "test", null)
        commentViewModel.deleteComment(roomId, commentId)

        // then
        val actual =
            (commentViewModel.commentResponseUiState.value as CommentResponseUiState.Succeed.Delete).comments.size
        val expected = 0
        assertEquals(expected, actual)
    }

    @Test
    fun `댓글 삭제 실패 시 commentResponseUiState 가 삭제 실패 상태로 바뀐다`() {
        // given
        val roomId: Long = 0
        val commentId: Long = 3
        val comment = Comment(id = commentId)

        coEvery {
            commentRepository.postComment(any(), any())
        } returns LogResult.success(comment)

        coEvery {
            commentRepository.deleteComment(any(), any())
        } returns LogResult.failure()

        // when
        commentViewModel.submitComment(roomId, "test", null)
        commentViewModel.deleteComment(roomId, commentId)

        // then
        val actual = commentViewModel.commentResponseUiState.value
        val expected = CommentResponseUiState.Failed.Delete
        assertEquals(expected, actual)
    }
}
