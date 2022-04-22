package com.ssafy.moabang.config

import android.app.Application
import android.os.Build
import com.kakao.sdk.common.KakaoSdk
import com.nhn.android.naverlogin.OAuthLogin
import com.ssafy.moabang.BuildConfig
import com.ssafy.moabang.R
import com.ssafy.moabang.src.login.LoginActivity

class GlobalApplication : Application() {
    private var instance: GlobalApplication? = null
    private var mGlobalUserLoginInfo: List<String?> = ArrayList()

    fun getGlobalApplicationContext(): GlobalApplication? {
        checkNotNull(instance) { "This Application does not GlobalAuthHelper" }
        return instance
    }

    fun getGlobalUserLoginInfo(): List<String?>? {
        return mGlobalUserLoginInfo
    }

    fun setGlobalUserLoginInfo(userLoginInfo: List<String?>) {
        mGlobalUserLoginInfo = userLoginInfo
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        KakaoSdk.init(this, BuildConfig.native_app_key)
    }

}