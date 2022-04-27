package com.ssafy.moabang.src.retrofitInterface

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST

interface loginService {

    @POST("/user/klogin")
    fun login(
        @Header("token") token : String?,
    ) : Call<Boolean>
}