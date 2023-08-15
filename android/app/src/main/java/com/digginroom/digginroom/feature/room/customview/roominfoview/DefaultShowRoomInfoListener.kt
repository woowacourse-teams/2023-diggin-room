package com.digginroom.digginroom.feature.room.customview.roominfoview

import com.digginroom.digginroom.feature.room.RoomActivity
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.TrackInfoDialog
import com.digginroom.digginroom.model.TrackModel

class DefaultShowRoomInfoListener(private val dialog: TrackInfoDialog, val activity: RoomActivity) :
    ShowRoomInfoListener {
    override fun show(trackModel: TrackModel) {
        if (dialog.isAdded) return
        dialog.show(activity.supportFragmentManager, "")
        dialog.updateTrackModel(trackModel)
    }
}
