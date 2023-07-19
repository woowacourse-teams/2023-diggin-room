package com.digginroom.digginroom.views.customView.roompager

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.ScrollView
import com.digginroom.digginroom.views.customView.roomview.YoutubeRoomView
import com.digginroom.digginroom.views.model.RoomModel

class RoomPager(
    context: Context,
    attributeSet: AttributeSet
) : ScrollView(context, attributeSet) {
    var rooms: List<RoomModel> = emptyList()
    private val roomViews: List<YoutubeRoomView> = (0 until 5).map {
        YoutubeRoomView(context)
    }

    init {
        val linearLayout = LinearLayout(context)
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        linearLayout.orientation = LinearLayout.VERTICAL
        roomViews.map {
            it.layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                1000
            )
            linearLayout.addView(it)
        }
        addView(linearLayout)
    }

    fun updateData(rooms: List<RoomModel>) {
        this.rooms = rooms
        rooms.mapIndexed { index, roomModel ->
            if (roomViews.size > index) {
                roomViews[index].navigate(roomModel)
            }
        }
    }
}
