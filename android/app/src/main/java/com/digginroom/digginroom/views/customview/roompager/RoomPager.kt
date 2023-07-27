package com.digginroom.digginroom.views.customview.roompager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.digginroom.digginroom.views.model.RoomModel

class RoomPager(
    context: Context,
    attributeSet: AttributeSet
) : FrameLayout(context, attributeSet) {

    private val verticalScrollPager: VerticalScrollPager = VerticalScrollPager(context)
    private val horizontalScrollPager: HorizontalScrollPager = HorizontalScrollPager(context)
    private val roomRecycler: RoomRecycler = RoomRecycler(context, GRID_SIZE)

    init {
        initVerticalScrollView()
        initHorizontalScrollView()
        initRoomRecycler()
    }

    fun updateData(rooms: List<RoomModel>) {
        roomRecycler.updateData(rooms)
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

        scrollPager.setOnTouchListener { event ->
            if (event.action != MotionEvent.ACTION_UP) return@setOnTouchListener
            when (scrollPager.pagingState) {
                PagingState.PREVIOUS -> {
                    roomRecycler.currentRoomPosition--
                    scrollPager.scrollPosition--
                }

                PagingState.CURRENT -> {}
                PagingState.NEXT -> {
                    roomRecycler.currentRoomPosition++
                    scrollPager.scrollPosition++
                }
            }

            if (roomRecycler.currentRoomPosition <= 0) {
                roomRecycler.currentRoomPosition = 0
            } else if (scrollPager.scrollPosition <= 0) {
                roomRecycler.recycleView(scrollPager)

                scrollPager.scrollPosition++

                scrollPager.scrollBy(
                    scrollPager.screenSize
                )
            } else if (scrollPager.scrollPosition >= GRID_SIZE - 1) {
                roomRecycler.onNextRoom()
                roomRecycler.recycleView(scrollPager)

                scrollPager.scrollPosition--

                scrollPager.scrollBy(
                    -scrollPager.screenSize
                )
            }

            roomRecycler.playCurrentRoomPlayer(verticalScrollPager.scrollPosition * GRID_SIZE + horizontalScrollPager.scrollPosition)
            scrollPager.post {
                scrollPager.smoothScrollTo(
                    scrollPager.scrollPosition * scrollPager.screenSize
                )
            }
        }
    }

    companion object {
        private const val GRID_SIZE = 3
        private const val PAGE_THRESHOLD = 10.0f
    }
}
