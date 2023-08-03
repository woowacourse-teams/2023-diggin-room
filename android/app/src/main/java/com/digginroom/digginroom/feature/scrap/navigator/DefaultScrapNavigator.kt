package com.digginroom.digginroom.feature.scrap.navigator

import android.content.Context
import com.digginroom.digginroom.feature.scrap.activity.ScrapRoomActivity
import com.digginroom.digginroom.model.RoomModel
import com.digginroom.digginroom.model.RoomModels

class DefaultScrapNavigator(
    private val context: Context
) : ScrapNavigator {

    override fun navigate(
        rooms: List<RoomModel>,
        position: Int
    ) {
        ScrapRoomActivity.start(
            context = context,
            rooms = RoomModels(rooms),
            position = position
        )
    }
}