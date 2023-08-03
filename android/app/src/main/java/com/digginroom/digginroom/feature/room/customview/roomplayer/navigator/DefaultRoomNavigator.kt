package com.digginroom.digginroom.feature.room.customview.roomplayer.navigator

import android.content.Context
import com.digginroom.digginroom.feature.scrap.activity.ScrapListActivity

class DefaultRoomNavigator(private val context: Context) : RoomNavigator {
    override fun navigateToScrap() {
        ScrapListActivity.start(context)
        (context as? ScrapListActivity)?.finish()
    }
}
