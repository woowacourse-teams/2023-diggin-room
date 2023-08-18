package com.digginroom.digginroom.feature.room.customview.roominfoview

import androidx.fragment.app.FragmentManager
import com.digginroom.digginroom.model.TrackModel

class DefaultShowRoomInfoListener(
    private val dialog: TrackInfoDialog,
    private val fragmentManager: FragmentManager
) :
    ShowRoomInfoListener {
    override fun show(trackModel: TrackModel) {
        if (dialog.isAdded) return
        dialog.show(fragmentManager, "")
        dialog.updateTrackModel(trackModel)
    }
}
