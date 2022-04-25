package com.ssafy.moabang.data.api

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApi {

    @POST("/user/login")
    fun login(
        @Header("token") token : String?,
    ) : Call<Boolean>
}