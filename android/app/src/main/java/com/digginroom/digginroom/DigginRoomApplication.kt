package com.digginroom.digginroom

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class DigginRoomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
    }
}
