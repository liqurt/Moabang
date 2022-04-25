package com.ssafy.moabang.data.repository


import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.ssafy.moabang.config.GlobalApplication
import com.ssafy.moabang.data.api.UserApi
import com.ssafy.moabang.src.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    val userApi: UserApi = GlobalApplication.retrofit.create(UserApi::class.java)

    fun login(token: OAuthToken, callback: LoginActivity) {
        userApi.login(token.accessToken).enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    callback.loginSuccess()
                } else {
                    Log.d("kakaoLogin", "onResponse: 알 수 없는 오류입니다.")
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("kakaoLogin", "onFailure: 서버 연결 오류")
            }
        })
    }
}