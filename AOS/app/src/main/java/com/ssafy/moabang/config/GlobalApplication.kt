package com.ssafy.moabang.config

import android.app.Application
import android.os.Build
import com.kakao.sdk.common.KakaoSdk
import com.nhn.android.naverlogin.OAuthLogin
import com.ssafy.moabang.BuildConfig
import com.ssafy.moabang.R
import com.ssafy.moabang.src.login.LoginActivity

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

        KakaoSdk.init(this, BuildConfig.native_app_key)

        val mOAuthLoginModule = OAuthLogin.getInstance()
        mOAuthLoginModule.init(this, BuildConfig.naver_client_id, BuildConfig.naver_client_secret, "모아방")
    }

    companion object {
        lateinit var instance: GlobalApplication
            private set
    }
}