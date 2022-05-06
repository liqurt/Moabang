package com.ssafy.moabang.data.api

import com.ssafy.moabang.data.model.dto.Theme
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ThemeApi {
    @GET("/cafe/theme/list")
    fun getAllTheme() : Call<List<Theme>>

    @GET("/theme/{themeid}/like")
    fun themeLike(
        @Path("themeid") tid: Int
    ) : Call<String>
}