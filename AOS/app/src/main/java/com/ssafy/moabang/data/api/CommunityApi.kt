package com.ssafy.moabang.data.api

import com.ssafy.moabang.data.model.dto.Community
import retrofit2.Call
import retrofit2.http.*

interface CommunityApi {
    @GET("/community")
    fun getAllCommunity() : Call<List<Community>>

    @POST("/community")
    fun insertCommunity(
        @Body content : String,
        @Body title : String,
    ) : Call<Boolean>

    @GET("/community/{rid}")
    fun getCommunity(
        @Path("rid") rid : Int
    ) : Call<Community>

    @POST("/community/{rid}")
    fun updateCommunity(
        @Path("rid") rid : Int,
        @Body content : String,
        @Body title : String,
    ) : Call<Boolean>

    @DELETE("/community/{rid}")
    fun deleteCommunity(
        @Path("rid") rid : Int
    ) : Call<Boolean>

    @GET("/community/new")
    fun getLatest5Community() : Call<List<Community>>

}