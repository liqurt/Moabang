package com.ssafy.moabang.src.retrofitInterface

import com.ssafy.moabang.data.model.dto.User
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST

interface loginService {

    @POST("/user/klogin")
    fun login(
        @Header("token") token : String?,
    ) : Call<User>
}