package com.ssafy.moabang.config

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kakao.sdk.common.KakaoSdk
import com.ssafy.moabang.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

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

    companion object {
        lateinit var instance: GlobalApplication
            private set

        lateinit var retrofit: Retrofit
            private set

        lateinit var gson: Gson
            private set
    }
}