package com.digginroom.digginroom.feature.room.customview.roomplayer

import android.content.Context
import com.digginroom.digginroom.feature.room.RoomActivity
import com.digginroom.digginroom.model.TrackModel

class DefaultShowRoomInfoListener(private val context: Context) : ShowRoomInfoListener {
    override fun show(trackModel: TrackModel) {
        val dialog = TrackInfoDialog(trackModel)
        dialog.isCancelable = true
        dialog.show((context as RoomActivity).supportFragmentManager, "")
    }
}
