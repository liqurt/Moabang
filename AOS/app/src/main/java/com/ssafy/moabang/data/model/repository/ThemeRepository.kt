package com.ssafy.moabang.data.model.repository


import android.util.Log
import com.ssafy.moabang.config.GlobalApplication
import com.ssafy.moabang.data.api.ThemeApi
import com.ssafy.moabang.data.model.response.ThemeListResponse
import com.ssafy.moabang.util.Resource
import java.lang.Exception

class ThemeRepository {
    val themeApi = GlobalApplication.retrofit.create(ThemeApi::class.java)

    suspend fun getAllTheme(jwtToken: String): Resource<ThemeListResponse> {
        return try {
            val response = themeApi.getAllTheme(jwtToken)
            Log.d("Theme Repository Test", "getAllTheme: themeApi success")
            if (response.isSuccessful) {
                return if (response.code() == 200 && response.body()!!.output == 1) {
                    Log.d("Theme Repository Test", "getAllTheme: response success")
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(response.body(), "error")
                }
            } else {
                Log.d("Theme Repository Test", "getAllTheme: response fail")
                Resource.error(null, "알 수 없는 오류입니다.")
            }
        } catch (e: Exception) {
            val msg = e.message
            Log.d("Theme Repository Test", "getAllTheme: error")
            Resource.error(null, "네트워크 상태를 확인해 주세요.")
        }
    }
}