package com.ssafy.moabang.data.model.repository


import android.util.Log
import com.ssafy.moabang.config.GlobalApplication
import com.ssafy.moabang.data.api.ReviewApi
import com.ssafy.moabang.data.api.ThemeApi
import com.ssafy.moabang.data.model.dto.ReviewForCreate
import com.ssafy.moabang.data.model.dto.Theme
import java.lang.Exception
import retrofit2.Response

class ReviewRepository {
    val reviewApi = GlobalApplication.retrofit.create(ReviewApi::class.java)

    suspend fun addReview(review: ReviewForCreate): Response<String>? {
        try{
            var res = reviewApi.addReview(review).execute()
            Log.d("Review Repository Test","성공")
            return res
        } catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }

}