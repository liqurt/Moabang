package com.ssafy.moabang.src.retrofitInterface

import com.ssafy.moabang.data.model.dto.Cafe
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface cafeService {
    @GET("/cafe/list")
    fun getAllCafe(
        @Header("token") jwtToken : String?,
    ) : Call<List<Cafe>>
}