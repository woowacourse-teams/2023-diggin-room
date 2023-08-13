package com.digginroom.digginroom.feature.genretaste

import android.content.Context
import android.util.Log
import com.digginroom.digginroom.feature.ResultListener
import com.digginroom.digginroom.feature.room.RoomActivity

class GenreTasteResultListener(private val context: Context) : ResultListener {

    override fun onSucceed() {
        RoomActivity.start(context)
    }

    override fun onFailed() {
        Log.d("woogi", "onFailed: genre taste survey failed")
    }
}
