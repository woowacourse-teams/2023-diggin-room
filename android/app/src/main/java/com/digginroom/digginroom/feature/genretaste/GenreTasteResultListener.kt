package com.digginroom.digginroom.feature.genretaste

import android.content.Context
import com.digginroom.digginroom.feature.ResultListener
import com.digginroom.digginroom.feature.room.RoomActivity

class GenreTasteResultListener(private val context: Context) : ResultListener {

    override fun onSucceed() {
        RoomActivity.start(context)
        (context as? GenreTasteActivity)?.finish()
    }

    override fun onFailed() {}
}
