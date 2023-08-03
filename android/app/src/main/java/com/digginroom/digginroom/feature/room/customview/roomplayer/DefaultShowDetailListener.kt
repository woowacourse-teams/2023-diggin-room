package com.digginroom.digginroom.feature.room.customview.roomplayer

import android.content.Context
import com.digginroom.digginroom.feature.room.RoomActivity

class DefaultShowDetailListener(private val context: Context) : ShowDetailListener {
    override fun show() {
        val dialog = TrackInfoDialog()
        dialog.isCancelable = true
        dialog.show((context as RoomActivity).supportFragmentManager, "")
    }
}
