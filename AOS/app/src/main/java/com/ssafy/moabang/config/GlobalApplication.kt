package com.ssafy.moabang.config

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.ssafy.moabang.BuildConfig
import com.ssafy.moabang.R

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

        KakaoSdk.init(this, BuildConfig.native_app_key)
    }

    companion object {
        lateinit var instance: GlobalApplication
            private set
    }
}