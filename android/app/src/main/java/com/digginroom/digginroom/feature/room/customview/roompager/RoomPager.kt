package com.digginroom.digginroom.feature.room.customview.roompager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.digginroom.digginroom.feature.room.customview.RoomRecycler
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

    init {
        initVerticalScrollView()
        initHorizontalScrollView()
        initRoomRecycler()
    }

    fun updateData(rooms: List<RoomModel>) {
        roomRecycler.updateData(rooms)
        navigateTargetRoom()
    }

    private fun initVerticalScrollView() {
        initScrollPager(verticalScrollPager)
        isVerticalScrollBarEnabled = false
        verticalScrollPager.layoutParams = LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        addView(verticalScrollPager)
    }

    private fun initHorizontalScrollView() {
        initScrollPager(horizontalScrollPager)
        horizontalScrollPager.layoutParams = LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        verticalScrollPager.addView(horizontalScrollPager)
    }

    private fun initRoomRecycler() {
        horizontalScrollPager.addView(roomRecycler)
    }

    private fun initScrollPager(scrollPager: ScrollPager) {
        initScrollMotionEvent(scrollPager)
        initScrollEndEvent(scrollPager)
    }

    private fun initScrollMotionEvent(scrollPager: ScrollPager) {
        val pagingBaseline = (scrollPager.screenSize / PAGE_THRESHOLD)
        scrollPager.setOnScrollChangeListener { scroll ->
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
        scrollPager.setOnTouchListener { event ->
            if (event.action != MotionEvent.ACTION_UP) return@setOnTouchListener
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
            roomRecycler.currentRoomPosition = 0
        } else if (scrollPager.scrollPosition <= 0) {
            roomRecycler.recyclePreviousRooms(scrollPager)
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
        playCurrentRoom()
        navigateTargetRoom()
        scrollPager.post {
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

    private fun calculateCurrentPosition(): Int =
        verticalScrollPager.scrollPosition * GRID_SIZE + horizontalScrollPager.scrollPosition

    companion object {
        private const val GRID_SIZE = 3
        private const val PAGE_THRESHOLD = 10.0f
    }
}
