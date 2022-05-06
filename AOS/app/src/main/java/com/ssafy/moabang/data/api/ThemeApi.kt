package com.ssafy.moabang.data.api

import com.ssafy.moabang.data.model.dto.Theme
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ThemeApi {
    @GET("/cafe/theme/list")
    fun getAllTheme(
        @Header("token") jwtToken : String?,
    ) : Call<List<Theme>>
}