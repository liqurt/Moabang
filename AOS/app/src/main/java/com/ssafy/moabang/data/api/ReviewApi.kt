package com.ssafy.moabang.data.api

import com.ssafy.moabang.data.model.dto.Theme
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewApi {
    @POST("/theme/review/create")
    fun addReview() : Call<String>
}