package com.digginroom.digginroom.views.customView.roompager

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.GridLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.view.size
import com.digginroom.digginroom.views.customView.roomview.YoutubeRoomView
import com.digginroom.digginroom.views.model.RoomModel

@SuppressLint("ClickableViewAccessibility")
class RoomPager(
    context: Context,
    attributeSet: AttributeSet
) : ScrollView(context, attributeSet) {

    private val horizontalScrollView: HorizontalScrollView = HorizontalScrollView(context)
    private val gridLayout: GridLayout = GridLayout(context)
    private var rooms: List<RoomModel> = emptyList()
    private val roomViews: List<YoutubeRoomView> = (0 until 9).map {
        YoutubeRoomView(context)
    }
    private val imageViews: List<TextView> = (0 until 9).map {
        TextView(context)
    }

    private val screenWidthSize = resources.displayMetrics.widthPixels
    private val screenHeightSize = resources.displayMetrics.heightPixels
    var currentRoomPosition = 0

    init {
        initScrollView()
        initHorizontalScrollView()
        initGridLayout()
        initContentView()
    }

    fun updateData(rooms: List<RoomModel>) {
        this.rooms = rooms
        rooms.mapIndexed { index, roomModel ->
            if (roomViews.size > index) {
                roomViews[index].navigate(roomModel)
            }
        }
    }

    private fun initScrollView() {
        var pagingState = PagingState.CURRENT
        var scrollPosition = 0
        setOnScrollChangeListener { _, _, scrollY, _, _ ->
            val headPosition = scrollPosition * screenHeightSize
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
                    scrollPosition--
                }

                PagingState.CURRENT -> {}
                PagingState.NEXT -> {
                    currentRoomPosition++
                    scrollPosition++
                }
            }

            if (currentRoomPosition <= 0) {
                currentRoomPosition = 0
            } else if (scrollPosition <= 0) {
                repeat(GRID_SIZE) {
                    val child = gridLayout.getChildAt(gridLayout.size - 1)
                    gridLayout.removeView(child)
                    gridLayout.addView(child, 0)
                }
                scrollPosition++
                scrollTo(0, (scrollPosition + 1) * screenHeightSize)
            } else if (scrollPosition >= GRID_SIZE - 1) {
                repeat(GRID_SIZE) {
                    val child = gridLayout.getChildAt(0)
                    gridLayout.removeView(child)
                    gridLayout.addView(child)
                }
                scrollPosition--
                scrollTo(0, (scrollPosition - 1) * screenHeightSize)
            }

            post {
                smoothScrollTo(0, scrollPosition * screenHeightSize)
            }

            false
        }

        isVerticalScrollBarEnabled = false
    }

    private fun initHorizontalScrollView() {
        var pagingState = PagingState.CURRENT
        var scrollPosition = 0
        horizontalScrollView.setOnScrollChangeListener { _, scrollX, _, _, _ ->
            val headPosition = scrollPosition * screenWidthSize
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
                    scrollPosition--
                }

                PagingState.CURRENT -> {}
                PagingState.NEXT -> {
                    currentRoomPosition++
                    scrollPosition++
                }
            }

            if (currentRoomPosition <= 0) {
                currentRoomPosition = 0
            } else if (scrollPosition <= 0) {
                repeat(GRID_SIZE) {
                    val child = gridLayout.getChildAt(it * GRID_SIZE + (GRID_SIZE - 1))
                    gridLayout.removeView(child)
                    gridLayout.addView(child, it * GRID_SIZE)
                }
                scrollPosition++
                horizontalScrollView.scrollTo(
                    (scrollPosition + 1) * screenWidthSize,
                    0
                )
            } else if (scrollPosition >= GRID_SIZE - 1) {
                repeat(GRID_SIZE) {
                    val child = gridLayout.getChildAt(it * GRID_SIZE)
                    gridLayout.removeView(child)
                    gridLayout.addView(child, it * GRID_SIZE + (GRID_SIZE - 1))
                }
                scrollPosition--
                horizontalScrollView.scrollTo(
                    (scrollPosition - 1) * screenWidthSize,
                    0
                )
            }

            horizontalScrollView.post {
                horizontalScrollView.smoothScrollTo(
                    scrollPosition * screenWidthSize,
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
        imageViews.mapIndexed { index, it ->
            it.layoutParams = LinearLayout.LayoutParams(
                screenWidthSize,
                screenHeightSize
            )
            it.text = index.toString()
            it.textSize = 500f
            gridLayout.addView(it)
        }
    }

    companion object {
        private const val GRID_SIZE = 3
        private const val PAGE_THRESHOLD = 10.0f
    }
}
