package com.digginroom.digginroom.logging

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class FirebaseLogger : Logger {
    private var firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    override fun log(logMessage: LogMessage) {
        val bundle = Bundle()
        bundle.putString(logMessage.tag, logMessage.body)
        firebaseAnalytics.logEvent(logMessage.level.name, bundle)
    }
}
