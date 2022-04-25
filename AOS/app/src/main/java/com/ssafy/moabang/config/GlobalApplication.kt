package com.ssafy.moabang.config

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.ssafy.moabang.BuildConfig
import com.ssafy.moabang.util.SharedPreferencesUtil
class GlobalApplication : Application() {
    private var instance: GlobalApplication? = null

    companion object{
        const val SHARED_PREFERENCES_NAME = "MOABANG"
        lateinit var sSharedPreferences: SharedPreferencesUtil
        const val COOKIES_KEY_NAME = "cookies"
        const val JWT = "JWT"
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        sSharedPreferences = SharedPreferencesUtil(applicationContext)
        KakaoSdk.init(this, BuildConfig.native_app_key)
    }

}