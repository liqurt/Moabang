package com.ssafy.moabang.config

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kakao.sdk.common.KakaoSdk
import com.ssafy.moabang.BuildConfig
import com.ssafy.moabang.data.model.repository.Repository
import com.ssafy.moabang.src.util.SharedPreferencesUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class GlobalApplication : Application() {
    val TIME_OUT = 10000L

    override fun onCreate() {
        super.onCreate()
        instance = this

        KakaoSdk.init(this, BuildConfig.native_app_key) // 카카오 세팅

        gson = GsonBuilder()
            .setLenient()
            .create()

        sp = SharedPreferencesUtil(applicationContext)

        retrofitInit()

        Repository.initialize(this)

    }

    fun retrofitInit(){
        // 승관홈 : http://114.129.238.28/
        // 서-버 : http://k6d205.p.ssafy.io:8080/
        // 모아방 : http://모아방.kr:8080/
        val serverURL="https://모아방.kr:8080/"

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
//            .addInterceptor(
//                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS).setLevel(
//                    HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(AuthInterceptor()) // JWT 자동 헤더 전송
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(serverURL)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
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

        lateinit var sp: SharedPreferencesUtil
    }
}