package com.digginroom.digginroom.views.customView.roompager

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.GridLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ScrollView
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

    init {
        val screenWidthSize = resources.displayMetrics.widthPixels
        val screenHeightSize = resources.displayMetrics.heightPixels
        var currentPosition = 0

        var verticalPagingState = PagingState.CURRENT
        var horizontalPagingState = PagingState.CURRENT

        setOnScrollChangeListener { _, _, scrollY, _, _ ->
            val headPosition = currentPosition * screenHeightSize
            val pagingBaseline = (screenHeightSize / 10.0f)
            verticalPagingState = if (scrollY < headPosition - pagingBaseline) {
                PagingState.PREVIOUS
            } else if (scrollY > headPosition + pagingBaseline) {
                PagingState.NEXT
            } else {
                PagingState.CURRENT
            }
        }
        setOnTouchListener { _, event ->
            if (event.action != MotionEvent.ACTION_UP) return@setOnTouchListener false
            when (verticalPagingState) {
                PagingState.PREVIOUS -> currentPosition--
                PagingState.CURRENT -> {}
                PagingState.NEXT -> currentPosition++
            }
            if (currentPosition <= 0) currentPosition = 0
            post {
                smoothScrollTo(scrollX, currentPosition * screenHeightSize)
            }
            false
        }

        horizontalScrollView.setOnScrollChangeListener { _, scrollX, _, _, _ ->
            val headPosition = currentPosition * screenWidthSize
            val pagingBaseline = (screenWidthSize / 10.0f)
            horizontalPagingState =
                if (scrollX < headPosition - pagingBaseline) {
                    PagingState.PREVIOUS
                } else if (scrollX > headPosition + pagingBaseline) {
                    PagingState.NEXT
                } else {
                    PagingState.CURRENT
                }
        }
        horizontalScrollView.setOnTouchListener { _, event ->
            if (event.action != MotionEvent.ACTION_UP) return@setOnTouchListener false
            when (horizontalPagingState) {
                PagingState.PREVIOUS -> currentPosition--
                PagingState.CURRENT -> {}
                PagingState.NEXT -> currentPosition++
            }
            if (currentPosition <= 0) currentPosition = 0
            horizontalScrollView.post {
                horizontalScrollView.smoothScrollTo(currentPosition * screenWidthSize, 0)
            }
            false
        }

        horizontalScrollView.layoutParams = LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        addView(horizontalScrollView)
        gridLayout.columnCount = 3
        gridLayout.rowCount = 3
        gridLayout.layoutParams = LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        horizontalScrollView.addView(gridLayout)
        roomViews.map {
            it.layoutParams = LinearLayout.LayoutParams(
                screenWidthSize,
                screenHeightSize
            )
            gridLayout.addView(it)
        }
    }

    fun updateData(rooms: List<RoomModel>) {
        this.rooms = rooms
//        rooms.mapIndexed { index, roomModel ->
//            if (roomViews.size > index) {
//                roomViews[index].navigate(roomModel)
//            }
//        }
    }
}
