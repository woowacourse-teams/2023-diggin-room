package com.digginroom.digginroom.feature.room.customview.roomplayer

import android.content.Context
import com.digginroom.digginroom.feature.scrap.ScrapActivity

class DefaultRoomNavigator(private val context: Context) : RoomNavigator {
    override fun navigateToScrap() {
        ScrapActivity.start(context)
        (context as? ScrapActivity)?.finish()
    }
}
