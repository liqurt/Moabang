package com.ssafy.moabang.data.model.repository


import android.util.Log
import com.ssafy.moabang.config.GlobalApplication
import com.ssafy.moabang.data.api.ThemeApi
import com.ssafy.moabang.data.model.dto.Theme
import java.lang.Exception
import retrofit2.Response

class ThemeRepository {
    val themeApi = GlobalApplication.retrofit.create(ThemeApi::class.java)
    var res: Response<List<Theme>>? = null

    suspend fun getAllTheme(jwtToken: String): Response<List<Theme>>? {
        try{
            res = themeApi.getAllTheme(jwtToken).execute()
            Log.d("Theme Repository Test","성공")
            return res
        } catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }
}