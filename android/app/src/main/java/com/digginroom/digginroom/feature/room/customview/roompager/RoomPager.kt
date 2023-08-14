package com.digginroom.digginroom.feature.room.customview.roompager

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.digginroom.digginroom.feature.room.RoomEventListener
import com.digginroom.digginroom.feature.room.customview.RoomRecycler
import com.digginroom.digginroom.feature.room.customview.roominfoview.ShowRoomInfoListener
import com.digginroom.digginroom.feature.room.customview.scrollpager.HorizontalScrollPager
import com.digginroom.digginroom.feature.room.customview.scrollpager.ScrollPager
import com.digginroom.digginroom.feature.room.customview.scrollpager.VerticalScrollPager
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

    init {
        initVerticalScrollView()
        initHorizontalScrollView()
        post {
            horizontalScrollPager.smoothScrollTo(
                horizontalScrollPager.scrollPosition * horizontalScrollPager.screenSize
            )
            updateRoomPosition(0)
        }
    }

    fun updateData(rooms: List<RoomModel>) {
        roomRecycler.updateData(rooms)
        navigateTargetRoom()
    }

    fun updateOrientation(pagingOrientation: PagingOrientation) {
        clearViewHierarchy()
        when (pagingOrientation) {
            PagingOrientation.BOTH -> {
                addView(verticalScrollPager)
                verticalScrollPager.addView(horizontalScrollPager)
                horizontalScrollPager.addView(roomRecycler)
            }

            PagingOrientation.VERTICAL -> {
                addView(verticalScrollPager)
                verticalScrollPager.addView(roomRecycler)
            }

            PagingOrientation.HORIZONTAL -> {
                addView(horizontalScrollPager)
                horizontalScrollPager.addView(roomRecycler)
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

    private fun clearViewHierarchy() {
        removeFirstChild(this)
        removeFirstChild(verticalScrollPager)
        removeFirstChild(horizontalScrollPager)
    }

    private fun removeFirstChild(viewGroup: ViewGroup) {
        if (viewGroup.childCount > 0) {
            viewGroup.removeViewAt(0)
        }
    }

    private fun initVerticalScrollView() {
        initScrollPager(verticalScrollPager)
        isVerticalScrollBarEnabled = false
        verticalScrollPager.scrollPosition = 0
        verticalScrollPager.layoutParams = LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
    }

    private fun initHorizontalScrollView() {
        initScrollPager(horizontalScrollPager)
        horizontalScrollPager.scrollPosition = GRID_SIZE / 2
        horizontalScrollPager.layoutParams = LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
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
        if (roomRecycler.currentRoomPosition <= 0) {
            scrollPager.scrollPosition = 0
            roomRecycler.navigateFirstRoom()
        } else if (scrollPager.scrollPosition <= 0) {
            roomRecycler.recyclePrevRooms(scrollPager)
            scrollPager.scrollPosition++
            scrollPager.scrollBy(scrollPager.screenSize)
        } else if (scrollPager.scrollPosition >= GRID_SIZE - 1) {
            roomRecycler.recycleNextRooms(scrollPager)
            scrollPager.scrollPosition--
            scrollPager.scrollBy(-scrollPager.screenSize)
            loadNextRoom()
        }
    }

    private fun pageToTargetRoom(scrollPager: ScrollPager) {
        navigateTargetRoom()
        playCurrentRoom()
        scrollPager.post {
            println(scrollPager.scrollPosition)
            scrollPager.smoothScrollTo(
                scrollPager.scrollPosition * scrollPager.screenSize
            )
        }
    }

    private fun playCurrentRoom() {
        val target = calculateCurrentPosition()
        roomRecycler.post {
            roomRecycler.playCurrentRoomPlayer(target)
        }
    }

    private fun navigateTargetRoom() {
        val target = calculateCurrentPosition()
        roomRecycler.navigateRooms(target)
    }

    private fun calculateCurrentPosition(): Int = verticalScrollPager.scrollPosition

    companion object {
        private const val GRID_SIZE = 3
        private const val PAGE_THRESHOLD = 10.0f
    }
}
