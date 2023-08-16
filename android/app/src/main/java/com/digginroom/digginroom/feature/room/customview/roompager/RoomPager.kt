package com.digginroom.digginroom.feature.room.customview.roompager

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.digginroom.digginroom.feature.room.RoomEventListener
import com.digginroom.digginroom.feature.room.customview.RoomRecycler
import com.digginroom.digginroom.feature.room.customview.roominfoview.ShowRoomInfoListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.ShowCommentsListener
import com.digginroom.digginroom.feature.room.customview.scrollpager.HorizontalScrollPager
import com.digginroom.digginroom.feature.room.customview.scrollpager.ScrollPager
import com.digginroom.digginroom.feature.room.customview.scrollpager.VerticalScrollPager
import com.digginroom.digginroom.model.CommentModel
import com.digginroom.digginroom.model.RoomModel

class RoomPager(
    context: Context,
    attributeSet: AttributeSet
) : FrameLayout(context, attributeSet) {

    private val verticalScrollPager: VerticalScrollPager = VerticalScrollPager(context)
    private val horizontalScrollPager: HorizontalScrollPager = HorizontalScrollPager(context)
    private val roomRecycler: RoomRecycler = RoomRecycler(context, GRID_SIZE)
    var loadNextRoom: () -> Unit = { }
    var lastPagingOrientation: PagingOrientation = PagingOrientation.VERTICAL
    var isNextRoomLoadable: Boolean = true

    init {
        initVerticalScrollView()
        initHorizontalScrollView()
        initOrientation()
    }

    fun updateData(rooms: List<RoomModel>) {
        roomRecycler.updateData(rooms)
        navigateTargetRoom()
    }

    fun updateOrientation(pagingOrientation: PagingOrientation) {
        when (pagingOrientation) {
            PagingOrientation.BOTH -> {
                horizontalScrollPager.isScrollable = true
                verticalScrollPager.isScrollable = true
            }

            PagingOrientation.VERTICAL -> {
                horizontalScrollPager.isScrollable = false
                verticalScrollPager.isScrollable = true
            }

            PagingOrientation.HORIZONTAL -> {
                horizontalScrollPager.isScrollable = true
                verticalScrollPager.isScrollable = false
            }
        }
    }

    fun updateRoomPosition(position: Int) {
        roomRecycler.currentRoomPosition = position
        navigateTargetRoom()
        playCurrentRoom()
    }

    fun updateOnScrapListener(callback: RoomEventListener) {
        roomRecycler.updateOnScrapListener(callback)
    }

    fun updateOnRemoveScrapListener(callback: RoomEventListener) {
        roomRecycler.updateOnRemoveScrapListener(callback)
    }

    fun updateDislikeListener(callback: RoomEventListener) {
        horizontalScrollPager.dislikeRoom = {
            callback.event(roomRecycler.currentRoomPlayerRoomId())
        }
    }

    fun updateShowInfoListener(showRoomInfoListener: ShowRoomInfoListener) {
        roomRecycler.updateShowRoomInfoListener(showRoomInfoListener)
    }

    private fun initOrientation() {
        post {
            horizontalScrollPager.scrollTo(
                horizontalScrollPager.scrollPosition * horizontalScrollPager.screenSize
            )
            verticalScrollPager.scrollTo(
                verticalScrollPager.scrollPosition * verticalScrollPager.screenSize
            )
            lastPagingOrientation = verticalScrollPager.pagingOrientation
            updateRoomPosition(0)
        }
    }

    fun updateOnFindCommentsListener(callback: RoomEventListener) {
        roomRecycler.updateOnFindCommentsListener(callback)
    }

    fun updateComments(roomId: Long, comments: List<CommentModel>) {
        roomRecycler.updateComments(roomId, comments)
    }

    fun updateShowCommentsListener(showCommentsListener: ShowCommentsListener) {
        roomRecycler.updateShowCommentsListener(showCommentsListener)
    }

    private fun initVerticalScrollView() {
        initScrollPager(verticalScrollPager)
        isVerticalScrollBarEnabled = false
        verticalScrollPager.scrollPosition = GRID_SIZE / 2
        verticalScrollPager.layoutParams = LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )

        addView(verticalScrollPager)
    }

    private fun initHorizontalScrollView() {
        initScrollPager(horizontalScrollPager)
        horizontalScrollPager.scrollPosition = GRID_SIZE / 2
        horizontalScrollPager.layoutParams = LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        verticalScrollPager.addView(horizontalScrollPager)
        horizontalScrollPager.addView(roomRecycler)
    }

    private fun initScrollPager(scrollPager: ScrollPager) {
        initScrollMotionEvent(scrollPager)
        initScrollEndEvent(scrollPager)
    }

    private fun initScrollMotionEvent(scrollPager: ScrollPager) {
        val pagingBaseline = (scrollPager.screenSize / PAGE_THRESHOLD)
        scrollPager.setOnScrollChangeListener { scroll ->
            if (lastPagingOrientation != scrollPager.pagingOrientation) {
                lastPagingOrientation = scrollPager.pagingOrientation
                roomRecycler.swapOrientation()
            }
            val headPosition = scrollPager.scrollPosition * scrollPager.screenSize
            scrollPager.pagingState = if (scroll < headPosition - pagingBaseline) {
                PagingState.PREVIOUS
            } else if (scroll > headPosition + pagingBaseline) {
                PagingState.NEXT
            } else {
                PagingState.CURRENT
            }
        }
    }

    private fun initScrollEndEvent(scrollPager: ScrollPager) {
        scrollPager.setOnTouchListener {
            determinePosition(scrollPager)
            recycleRooms(scrollPager)
            pageToTargetRoom(scrollPager)
        }
    }

    private fun determinePosition(scrollPager: ScrollPager) {
        when (scrollPager.pagingState) {
            PagingState.PREVIOUS -> {
                roomRecycler.currentRoomPosition--
                scrollPager.scrollPosition--
            }

            PagingState.CURRENT -> Unit
            PagingState.NEXT -> {
                roomRecycler.currentRoomPosition++
                scrollPager.scrollPosition++
            }
        }
    }

    private fun recycleRooms(scrollPager: ScrollPager) {
        if (roomRecycler.currentRoomPosition == roomRecycler.roomSize() - 2) loadNextRoom()
        if (roomRecycler.currentRoomPosition < 0) {
            scrollPager.scrollPosition = 1
            roomRecycler.navigateFirstRoom()
        } else if (roomRecycler.currentRoomPosition >= roomRecycler.roomSize() && !isNextRoomLoadable) {
            scrollPager.scrollPosition = 1
            roomRecycler.navigateLastRoom()
        } else if (scrollPager.scrollPosition <= 0) {
            roomRecycler.recyclePrevRooms(scrollPager)
            scrollPager.scrollPosition++
            scrollPager.scrollBy(scrollPager.screenSize)
        } else if (scrollPager.scrollPosition >= GRID_SIZE - 1) {
            roomRecycler.recycleNextRooms(scrollPager)
            scrollPager.scrollPosition--
            scrollPager.scrollBy(-scrollPager.screenSize)
        }
    }

    private fun pageToTargetRoom(scrollPager: ScrollPager) {
        navigateTargetRoom()
        playCurrentRoom()
        scrollPager.post {
            scrollPager.smoothScrollTo(
                scrollPager.scrollPosition * scrollPager.screenSize
            )
        }
    }

    private fun playCurrentRoom() {
        roomRecycler.post {
            roomRecycler.playCurrentRoomPlayer()
        }
    }

    private fun navigateTargetRoom() {
        roomRecycler.navigateRooms()
    }

    companion object {
        private const val GRID_SIZE = 3
        private const val PAGE_THRESHOLD = 10.0f
    }
}
