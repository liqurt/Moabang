package com.ssafy.moabang.data.model.repository


import android.util.Log
import com.ssafy.moabang.config.GlobalApplication
import com.ssafy.moabang.data.api.MyPageApi
import com.ssafy.moabang.data.model.dto.Theme
import java.lang.Exception
import retrofit2.Response

class MyPageRepository {
    val mypageApi = GlobalApplication.retrofit.create(MyPageApi::class.java)

    fun getAllLikeTheme(): Response<List<Theme>>? {
        try{
            var res = mypageApi.getAllLikeTheme().execute()
            Log.d("Mypage Repository Test","성공")
            return res
        } catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }

}