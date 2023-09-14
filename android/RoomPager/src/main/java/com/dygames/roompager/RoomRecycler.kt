package com.dygames.roompager

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.LinearLayout
import com.dygames.roompager.scrollpager.ScrollPager

@SuppressLint("ViewConstructor")
class RoomRecycler(
    context: Context,
    private val gridSize: Int
) : GridLayout(context) {

    private lateinit var adapter: Adapter<Adapter.ViewHolder>
    private val viewHolders: List<Adapter.ViewHolder> by lazy {
        (0 until gridSize).map {
            adapter.createViewHolder(context)
        }
    }

    fun setAdapter(adapter: Adapter<Adapter.ViewHolder>) {
        this.adapter = adapter

        initLayout()
        initContentView()
    }

    fun recyclePrevRooms(scrollPager: ScrollPager, currentRoomPosition: Int) {
        val start = scrollPager.calculateStartChildPosition(gridSize)
        val end = scrollPager.calculateEndChildPosition(gridSize)
        val first = getChildAt(start)
        val second = getChildAt(calculateCenter())
        val third = getChildAt(end)
        removeView(first)
        removeView(second)
        removeView(third)
        addView(third, start)
        addView(first, calculateCenter())
        addView(second, end)
        adapter.onRecycle(
            currentRoomPosition,
            listOf(third, first, second).map { it as Adapter.ViewHolder }
        )
    }

    fun recycleNextRooms(scrollPager: ScrollPager, currentRoomPosition: Int) {
        val start = scrollPager.calculateStartChildPosition(gridSize)
        val end = scrollPager.calculateEndChildPosition(gridSize)
        val first = getChildAt(start)
        val second = getChildAt(calculateCenter())
        val third = getChildAt(end)
        removeView(first)
        removeView(second)
        removeView(third)
        addView(second, start)
        addView(third, calculateCenter())
        addView(first, end)
        adapter.onRecycle(
            currentRoomPosition,
            listOf(second, third, first).map { it as Adapter.ViewHolder }
        )
    }

    fun navigate(scrollPager: ScrollPager, currentRoomPosition: Int) {
        val start = scrollPager.calculateStartChildPosition(gridSize)
        val end = scrollPager.calculateEndChildPosition(gridSize)
        val first = getChildAt(start)
        val second = getChildAt(calculateCenter())
        val third = getChildAt(end)
        adapter.onRecycle(
            currentRoomPosition,
            listOf(first, second, third).map { it as Adapter.ViewHolder }
        )
    }

    fun swapOrientation() {
        swapView(1, 3)
        swapView(5, 7)
    }

    fun roomSize(): Int = adapter.getItemCount()

    fun loadMore() {
        adapter.onLoadNextRoom()
    }

    private fun swapView(start: Int, end: Int) {
        val first = getChildAt(start)
        val second = getChildAt(end)
        removeView(first)
        removeView(second)
        addView(second, start)
        addView(first, end)
    }

    private fun initLayout() {
        columnCount = gridSize
        rowCount = gridSize
        layoutParams = LinearLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
    }

    private fun initContentView() {
        (0 until gridSize * gridSize).forEach {
            val view =
                if (it % gridSize == gridSize / 2) {
                    (viewHolders[it / gridSize] as View)
                } else {
                    View(
                        context
                    )
                }
            view.layoutParams = LinearLayout.LayoutParams(
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels
            )
            addView(view)
        }
        adapter.onRecycle(0, viewHolders)
    }

    private fun calculateCenter() = (gridSize * gridSize) / 2
}
