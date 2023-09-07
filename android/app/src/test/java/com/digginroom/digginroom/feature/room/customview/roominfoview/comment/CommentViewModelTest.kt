package com.digginroom.digginroom.feature.room.customview.roominfoview.comment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
        val defaultComments = Comments()
        val roomId: Long = 0

        coEvery {
            commentRepository.findComments(any())
        } returns LogResult.success(defaultComments)

        // when
        commentViewModel.findComments(roomId)

        // then
        val actual = commentViewModel.comments.value
        val expected = defaultComments.map { it.toModel() }
        assertEquals(actual, expected)
    }

    @Test
    fun `댓글 작성을 요청하면 새 댓글이 생성된다`() {
        // given
        val defaultComment = Comment()
        val roomId: Long = 0

        coEvery {
            commentRepository.postComment(any(), any())
        } returns LogResult.success(defaultComment)

        // when
        commentViewModel.postComment(roomId, defaultComment.comment)

        // then
        val actual = commentViewModel.comments.value
        val expected = listOf(defaultComment.toModel())
        assertEquals(actual, expected)
    }

    @Test
    fun `댓글 작성 요청에 실패하면 commentRequestState가 Failed 이 된다`() {
        // given
        val defaultComment = Comment()
        val roomId: Long = 0

        coEvery {
            commentRepository.postComment(any(), any())
        } returns LogResult.failure()

        // when
        commentViewModel.postComment(roomId, defaultComment.comment)

        // then
        val actual = commentViewModel.commentRequestState.value
        assertTrue(actual is CommentRequestState.Failed)
    }

    @Test
    fun `댓글 삭제를 요청하면 댓글이 삭제된다`() {
        // given
        val roomId: Long = 0
        val commentId: Long = 0
        val deletedPosition = 0
        val defaultComments = Comments()
        val commentCount = defaultComments.size

        coEvery {
            commentRepository.findComments(any())
        } returns LogResult.success(defaultComments)
        coEvery {
            commentRepository.deleteComment(any(), any())
        } returns LogResult.success(Unit)

        // when
        commentViewModel.findComments(roomId)
        commentViewModel.deleteComment(roomId, commentId, deletedPosition)

        // then
        val actual = commentViewModel.comments.value ?: listOf()
        assertEquals(commentCount - 1, actual.size)
    }
}
