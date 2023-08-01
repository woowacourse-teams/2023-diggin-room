package com.digginroom.digginroom.views.customview.roompager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import com.digginroom.digginroom.views.model.RoomModel

class ScrappedRoomPager(
    context: Context,
    attributeSet: AttributeSet
) : FrameLayout(context, attributeSet) {

    private val verticalScrollPager: VerticalScrollPager = VerticalScrollPager(context)
    private val roomRecycler: ScrappedRoomRecycler = ScrappedRoomRecycler(context, 3)

    init {
        initScrollView()
        initRoomRecycler()
    }

    fun updateData(rooms: List<RoomModel>) {
        roomRecycler.updateData(rooms)
        navigateTargetRoom()
    }

    private fun initScrollView() {
        initScrollPager()
        isVerticalScrollBarEnabled = false
        verticalScrollPager.layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        addView(verticalScrollPager)
    }

    private fun initRoomRecycler() {
        verticalScrollPager.addView(roomRecycler)
    }

    private fun initScrollPager() {
        initScrollMotionEvent()
        initScrollEndEvent()
    }

    private fun initScrollMotionEvent() {
        /**
         * scrollAmount: view내에서 scroll이 일어나고 있는 Y포지션
         * headPosition: 화면의 가장 상단을 의미한다. (현재 gridLayout 내에서 y position? * screenSize)
         * scrollBaseline: scroll event가 동작하기 위한 최소 스크롤 거리
         */
        with(verticalScrollPager) {
            val pagingBaseline = (screenSize / PAGE_THRESHOLD)
            val headPosition = scrollPosition * screenSize
            setOnScrollChangeListener { scrollAmount ->
                pagingState = if (scrollAmount < headPosition - pagingBaseline) {
                    PagingState.PREVIOUS
                } else if (scrollAmount > headPosition + pagingBaseline) {
                    PagingState.NEXT
                } else {
                    PagingState.CURRENT
                }
            }
        }
    }

    /**
     * 터치가 끝난 후(스크롤이 끝난 후) 이벤트에 대한 정의
     */
    private fun initScrollEndEvent() {
        verticalScrollPager.setOnTouchListener { event ->
            if (event.action != MotionEvent.ACTION_UP) return@setOnTouchListener
            determinePosition()
            recycleRooms()
            pageToTargetRoom()
        }
    }

    private fun determinePosition() {
        when (verticalScrollPager.pagingState) {
            PagingState.PREVIOUS -> {
                roomRecycler.currentRoomPosition--
                verticalScrollPager.scrollPosition--
            }

            PagingState.NEXT -> {
                roomRecycler.currentRoomPosition++
                verticalScrollPager.scrollPosition++
            }

            PagingState.CURRENT -> {}
        }
    }

    private fun recycleRooms() {
        if (roomRecycler.currentRoomPosition <= 0) {
            roomRecycler.currentRoomPosition = 0
        } else if (verticalScrollPager.scrollPosition <= 0) {
            roomRecycler.recyclePreviousRooms()
            verticalScrollPager.scrollPosition++
            /**
             * 가운데를 계속 유지한다.
             * prevRoom
             * currentRoom
             * nextRoom
             */
            verticalScrollPager.scrollBy(verticalScrollPager.screenSize)
        } else if (verticalScrollPager.scrollPosition >= 2) {
            roomRecycler.recycleNextRooms()
            verticalScrollPager.scrollPosition--
            verticalScrollPager.scrollBy(-verticalScrollPager.screenSize)
        }
    }

    private fun pageToTargetRoom() {
        navigateTargetRoom()
        with(verticalScrollPager) {
            post {
                smoothScrollTo(scrollPosition * screenSize)
            }
        }
    }

    private fun navigateTargetRoom() {
        val target = verticalScrollPager.scrollPosition

        roomRecycler.playCurrentRoomPlayer(target)
        roomRecycler.navigateRooms(target)
    }

    companion object {

        private const val PAGE_THRESHOLD = 10.0f
    }
}
