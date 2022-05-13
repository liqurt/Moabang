package com.ssafy.moabang.data.api

import com.ssafy.moabang.data.model.dto.Theme
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPageApi {
    @GET("/mypage/theme/list")
    fun getAllLikeTheme() : Call<List<Theme>>
}