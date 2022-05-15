package com.ssafy.moabang.data.api

import com.ssafy.moabang.data.model.dto.Community
import com.ssafy.moabang.data.model.dto.RecruitCreateRequest
import retrofit2.Call
import retrofit2.http.*

interface CommunityApi {
    @GET("/community/read/list")
    fun getAllCommunity() : Call<List<Community>>

    @POST("/community/write")
    fun insertCommunity(
        @Body requestCreateRequest: RecruitCreateRequest
    ) : Call<Boolean>

    @GET("/community/read/{rid}")
    fun getCommunity(
        @Path("rid") rid : Int
    ) : Call<Community>

    @PUT("/community/update/{rid}")
    fun updateCommunity(
        @Path("rid") rid : Int,
        @Body requestCreateRequest: RecruitCreateRequest
    ) : Call<Boolean>

    @DELETE("/community/delete/{rid}")
    fun deleteCommunity(
        @Path("rid") rid : Int
    ) : Call<Boolean>

    @GET("/community/new")
    fun getLatest5Community() : Call<List<Community>>

}