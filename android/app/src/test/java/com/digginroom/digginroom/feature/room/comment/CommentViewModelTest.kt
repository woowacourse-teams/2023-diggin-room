package com.digginroom.digginroom.feature.room.comment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.digginroom.digginroom.feature.room.comment.uistate.CommentResponseUiState
import com.digginroom.digginroom.fixture.CommentFixture.Comment
import com.digginroom.digginroom.fixture.CommentFixture.Comments
import com.digginroom.digginroom.fixture.LogResult
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
import org.junit.Assert.assertTrue
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
    fun `해당 룸의 댓글 요청 시 성공하면 성공 상태와 함께 댓글을 받아온다`() {
        // given
        val roomId: Long = 0

        coEvery {
            commentRepository.findComments(any())
        } returns LogResult.success(comments)

        // when
        commentViewModel.findComments(roomId)

        // then
        val actual = commentViewModel.commentResponseUiState.value as CommentResponseUiState.Succeed
        val expected = comments.map { it.toModel() }
        assertEquals(expected, actual.comments)
    }

    @Test
    fun `댓글 작성을 요청하면 새 댓글이 생성된다`() {
        // given
        val comment = Comment(id = 3)
        val roomId: Long = 0

        coEvery {
            commentRepository.postComment(any(), any())
        } returns LogResult.success(comment)

        // when
        commentViewModel.submitComment(roomId, comment.comment, null)

        // then
        val actual = commentViewModel.commentResponseUiState.value as CommentResponseUiState.Succeed
        val expected = listOf(comment.toModel())
        assertEquals(expected, actual.comments)
    }

    @Test
    fun `댓글 작성 요청에 실패하면 commentRequestState가 Failed 된다`() {
        // given
        val comment = comments[0]
        val roomId: Long = 0

        coEvery {
            commentRepository.postComment(any(), any())
        } returns LogResult.failure()

        // when
        commentViewModel.submitComment(roomId, comment.comment, null)

        // then
        val actual = commentViewModel.commentResponseUiState.value
        assertTrue(actual is CommentResponseUiState.Failed)
    }

    @Test
    fun `댓글 삭제를 요청하면 댓글이 삭제된다`() {
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
            (commentViewModel.commentResponseUiState.value as CommentResponseUiState.Succeed).comments.size
        val expected = 0
        assertEquals(expected, actual)
    }
}
