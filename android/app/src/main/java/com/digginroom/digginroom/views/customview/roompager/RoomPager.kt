package com.digginroom.digginroom.views.customview.roompager

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.forEachIndexed
import com.digginroom.digginroom.views.customview.roomview.RoomPlayer
import com.digginroom.digginroom.views.customview.roomview.YoutubeRoomPlayer
import com.digginroom.digginroom.views.model.RoomModel

class RoomPager(
    context: Context, attributeSet: AttributeSet
) : FrameLayout(context, attributeSet) {

    private val verticalScrollPager: VerticalScrollPager = VerticalScrollPager(context)
    private val horizontalScrollPager: HorizontalScrollPager = HorizontalScrollPager(context)
    private val roomRecycler: RoomRecycler = RoomRecycler(context, GRID_SIZE)


    private val scrollPosition: Point = Point(0, 0)
    private var targetRooms: List<Point> = listOf(Point(0, 0), Point(0, 1), Point(1, 0))
    private var rooms: List<RoomModel> = emptyList()
    private var currentRoomPosition = 0
    var onNextRoom: () -> Unit = { }

    init {
        initVerticalScrollView()
        initHorizontalScrollView()
        initRoomRecycler()
    }

    fun updateData(rooms: List<RoomModel>) {
        this.rooms = rooms
        navigateRooms()
    }

    private fun initVerticalScrollView() {
        initScrollPager(verticalScrollPager)

        isVerticalScrollBarEnabled = false

        verticalScrollPager.layoutParams = LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
        )
        addView(verticalScrollPager)
    }

    private fun initHorizontalScrollView() {
        initScrollPager(horizontalScrollPager)

        horizontalScrollPager.layoutParams = LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
        )
        addView(horizontalScrollPager)
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
                    currentRoomPosition--
                    scrollPager.scrollPosition--
                }

                PagingState.CURRENT -> {}
                PagingState.NEXT -> {
                    currentRoomPosition++
                    scrollPager.scrollPosition++
                }
            }

            if (currentRoomPosition <= 0) {
                currentRoomPosition = 0
            } else if (scrollPager.scrollPosition <= 0) {
                repeat(GRID_SIZE) {
                    val child = roomRecycler.getChildAt(
                        scrollPager.calculateEndChildPosition(
                            it, GRID_SIZE
                        )
                    )
                    roomRecycler.removeView(child)
                    roomRecycler.addView(
                        child, scrollPager.calculateStartChildPosition(it, GRID_SIZE)
                    )
                }

                scrollPager.scrollPosition++

                scrollPager.scrollBy(
                    scrollPager.screenSize
                )
            } else if (scrollPosition.x >= GRID_SIZE - 1) {
                onNextRoom()
                repeat(GRID_SIZE) {
                    val child = roomRecycler.getChildAt(
                        scrollPager.calculateStartChildPosition(
                            it, GRID_SIZE
                        )
                    )
                    roomRecycler.removeView(child)
                    roomRecycler.addView(
                        child, scrollPager.calculateEndChildPosition(it, GRID_SIZE)
                    )
                }

                scrollPager.scrollPosition--

                horizontalScrollPager.scrollBy(
                    -scrollPager.screenSize
                )
            }

            targetRooms = listOf(
                Point(scrollPosition.x - 1, scrollPosition.y),
                Point(scrollPosition.x, scrollPosition.y),
                Point(scrollPosition.x + 1, scrollPosition.y)
            )

            playCurrentRoomPlayer()
            scrollPager.post {
                scrollPager.smoothScrollTo(
                    scrollPosition.x * scrollPager.screenSize
                )
            }
        }
    }

    private fun playCurrentRoomPlayer() {
        roomRecycler.forEachIndexed { index, view ->
            if (index == scrollPosition.y * GRID_SIZE + scrollPosition.x) {
                (view as YoutubeRoomPlayer).play()
            } else {
                (view as YoutubeRoomPlayer).pause()
            }
        }
    }

    private fun navigateRooms() {
        targetRooms.forEachIndexed { index, point ->
            val recyclePosition = currentRoomPosition + index - 1
            if (recyclePosition in rooms.indices) (roomRecycler.getChildAt(pointToScalar(point)) as RoomPlayer).navigate(
                    rooms[recyclePosition]
                )
        }
    }

    private fun initRoomRecycler() {
        horizontalScrollPager.addView(roomRecycler)
    }

    private fun pointToScalar(point: Point): Int {
        return point.y * GRID_SIZE + point.x
    }

    companion object {
        private const val GRID_SIZE = 3
        private const val PAGE_THRESHOLD = 10.0f
    }
}
