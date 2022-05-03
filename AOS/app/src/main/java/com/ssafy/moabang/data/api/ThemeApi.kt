package com.ssafy.moabang.data.api

import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.data.model.response.ThemeListResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ThemeApi {
    @GET("/cafe/theme/list")
    fun getAllTheme(
        @Header("token") jwtToken : String?,
    ) : Response<ThemeListResponse>
}