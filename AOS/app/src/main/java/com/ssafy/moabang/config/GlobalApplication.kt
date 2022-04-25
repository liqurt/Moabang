package com.ssafy.moabang.config

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kakao.sdk.common.KakaoSdk
import com.ssafy.moabang.BuildConfig
import com.ssafy.moabang.util.SharedPreferencesUtil
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GlobalApplication : Application() {
    private var instance: GlobalApplication? = null

    companion object{
        lateinit var instance: GlobalApplication
            private set

        const val SHARED_PREFERENCES_NAME = "MOABANG"
        lateinit var sSharedPreferences: SharedPreferencesUtil
        const val COOKIES_KEY_NAME = "cookies"
        const val JWT = "JWT"

        lateinit var retrofit: Retrofit
            private set

        lateinit var gson: Gson
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this


        sSharedPreferences = SharedPreferencesUtil(applicationContext)
        KakaoSdk.init(this, BuildConfig.native_app_key)


        KakaoSdk.init(this, BuildConfig.native_app_key) // 카카오 세팅

        val serverURL="http://114.129.238.28" // 승관의 집

        gson = GsonBuilder()
            .setLenient()
            .create()

        retrofit = Retrofit.Builder()
            .baseUrl(serverURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    }

}