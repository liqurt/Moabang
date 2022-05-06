package com.ssafy.moabang.data.model.repository


import android.util.Log
import com.ssafy.moabang.config.GlobalApplication
import com.ssafy.moabang.data.api.ThemeApi
import com.ssafy.moabang.data.model.dto.Theme
import java.lang.Exception
import retrofit2.Response

class ThemeRepository {
    val themeApi = GlobalApplication.retrofit.create(ThemeApi::class.java)

    suspend fun getAllTheme(): Response<List<Theme>>? {
        try{
            var res = themeApi.getAllTheme().execute()
            Log.d("Theme Repository Test","성공")
            return res
        } catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }

    suspend fun themeLike(tid: Int) : Response<String>? {
        try{
           var res = themeApi.themeLike(tid).execute()
            Log.d("Theme LIKE Test","성공 $res")
            return res
        } catch(e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}