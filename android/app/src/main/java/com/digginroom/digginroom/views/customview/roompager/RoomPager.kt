package com.digginroom.digginroom.views.customview.roompager

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.GridLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.core.view.forEachIndexed
import androidx.core.view.size
import com.digginroom.digginroom.views.customview.roomview.RoomPlayer
import com.digginroom.digginroom.views.customview.roomview.YoutubeRoomPlayer
import com.digginroom.digginroom.views.model.RoomModel

@SuppressLint("ClickableViewAccessibility")
class RoomPager(
    context: Context,
    attributeSet: AttributeSet
) : ScrollView(context, attributeSet) {

    private val horizontalScrollView: HorizontalScrollView = HorizontalScrollView(context)
    private val gridLayout: GridLayout = GridLayout(context)
    private val roomPlayers: List<YoutubeRoomPlayer> = (0 until GRID_SIZE * GRID_SIZE).map {
        YoutubeRoomPlayer(context)
    }

    private val scrollPosition: Point = Point(0, 0)
    private var targetRooms: List<Point> = listOf(Point(0, 0), Point(0, 1))
    private var rooms: List<RoomModel> = emptyList()
    var onNextRoom: () -> Unit = { }
    var currentRoomPosition = 0

    init {
        initScrollView()
        initHorizontalScrollView()
        initGridLayout()
        initContentView()
    }

    fun updateData(rooms: List<RoomModel>) {
        this.rooms = rooms
        navigateRooms()
    }

    private fun initScrollView() {
        var pagingState = PagingState.CURRENT
        val screenHeightSize = resources.displayMetrics.heightPixels

        setOnScrollChangeListener { _, _, scrollY, _, _ ->
            val headPosition = scrollPosition.y * screenHeightSize
            val pagingBaseline = (screenHeightSize / PAGE_THRESHOLD)
            pagingState = if (scrollY < headPosition - pagingBaseline) {
                PagingState.PREVIOUS
            } else if (scrollY > headPosition + pagingBaseline) {
                PagingState.NEXT
            } else {
                PagingState.CURRENT
            }
        }

        setOnTouchListener { _, event ->
            if (event.action != MotionEvent.ACTION_UP) return@setOnTouchListener false
            when (pagingState) {
                PagingState.PREVIOUS -> {
                    currentRoomPosition--
                    scrollPosition.y--
                }

                PagingState.CURRENT -> {}
                PagingState.NEXT -> {
                    currentRoomPosition++
                    scrollPosition.y++
                }
            }

            if (currentRoomPosition <= 0) {
                currentRoomPosition = 0
            } else if (scrollPosition.y <= 0) {
                repeat(GRID_SIZE) {
                    val child = gridLayout.getChildAt(gridLayout.size - 1)
                    gridLayout.removeView(child)
                    gridLayout.addView(child, 0)
                }

                scrollPosition.y++

                scrollBy(0, screenHeightSize)
            } else if (scrollPosition.y >= GRID_SIZE - 1) {
                onNextRoom()
                repeat(GRID_SIZE) {
                    val child = gridLayout.getChildAt(0)
                    gridLayout.removeView(child)
                    gridLayout.addView(child)
                }

                scrollPosition.y--

                scrollBy(0, -screenHeightSize)
            }

            targetRooms = listOf(
                Point(scrollPosition.x, scrollPosition.y - 1),
                Point(scrollPosition.x, scrollPosition.y),
                Point(scrollPosition.x, scrollPosition.y + 1)
            )

            playCurrentRoomPlayer()
            post {
                smoothScrollTo(0, scrollPosition.y * screenHeightSize)
            }
            false
        }

        isVerticalScrollBarEnabled = false
    }

    private fun initHorizontalScrollView() {
        var pagingState = PagingState.CURRENT
        val screenWidthSize = resources.displayMetrics.widthPixels

        horizontalScrollView.setOnScrollChangeListener { _, scrollX, _, _, _ ->
            val headPosition = scrollPosition.x * screenWidthSize
            val pagingBaseline = (screenWidthSize / PAGE_THRESHOLD)
            pagingState = if (scrollX < headPosition - pagingBaseline) {
                PagingState.PREVIOUS
            } else if (scrollX > headPosition + pagingBaseline) {
                PagingState.NEXT
            } else {
                PagingState.CURRENT
            }
        }
        horizontalScrollView.setOnTouchListener { _, event ->
            if (event.action != MotionEvent.ACTION_UP) return@setOnTouchListener false
            when (pagingState) {
                PagingState.PREVIOUS -> {
                    currentRoomPosition--
                    scrollPosition.x--
                }

                PagingState.CURRENT -> {}
                PagingState.NEXT -> {
                    currentRoomPosition++
                    scrollPosition.x++
                }
            }

            if (currentRoomPosition <= 0) {
                currentRoomPosition = 0
            } else if (scrollPosition.x <= 0) {
                repeat(GRID_SIZE) {
                    val child = gridLayout.getChildAt(it * GRID_SIZE + (GRID_SIZE - 1))
                    gridLayout.removeView(child)
                    gridLayout.addView(child, it * GRID_SIZE)
                }

                scrollPosition.x++

                horizontalScrollView.scrollBy(
                    screenWidthSize,
                    0
                )
            } else if (scrollPosition.x >= GRID_SIZE - 1) {
                onNextRoom()
                repeat(GRID_SIZE) {
                    val child = gridLayout.getChildAt(it * GRID_SIZE)
                    gridLayout.removeView(child)
                    gridLayout.addView(child, it * GRID_SIZE + (GRID_SIZE - 1))
                }

                scrollPosition.x--

                horizontalScrollView.scrollBy(
                    -screenWidthSize,
                    0
                )
            }

            targetRooms = listOf(
                Point(scrollPosition.x - 1, scrollPosition.y),
                Point(scrollPosition.x, scrollPosition.y),
                Point(scrollPosition.x + 1, scrollPosition.y)
            )
            playCurrentRoomPlayer()
            horizontalScrollView.post {
                horizontalScrollView.smoothScrollTo(
                    scrollPosition.x * screenWidthSize,
                    0
                )
            }

            false
        }

        horizontalScrollView.layoutParams = LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        addView(horizontalScrollView)
    }

    private fun playCurrentRoomPlayer() {
        gridLayout.forEachIndexed { index, view ->
            if (index == scrollPosition.y * GRID_SIZE + scrollPosition.x) {
                (view as YoutubeRoomPlayer).play()
            } else {
                (view as YoutubeRoomPlayer).pause()
            }
        }
    }

    private fun navigateRooms() {
        targetRooms.forEachIndexed { index, point ->
            (
                gridLayout.getChildAt(
                    pointToScalar(point)
                ) as RoomPlayer
                ).navigate(rooms[currentRoomPosition + index - 1])
        }
    }

    private fun initGridLayout() {
        gridLayout.columnCount = GRID_SIZE
        gridLayout.rowCount = GRID_SIZE
        gridLayout.layoutParams = LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )

        horizontalScrollView.addView(gridLayout)
    }

    private fun initContentView() {
        roomPlayers.map {
            it.layoutParams = LinearLayout.LayoutParams(
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels
            )
            gridLayout.addView(it)
        }
    }

    private fun pointToScalar(point: Point): Int {
        return point.y * GRID_SIZE + point.x
    }

    companion object {
        private const val GRID_SIZE = 3
        private const val PAGE_THRESHOLD = 10.0f
    }
}
