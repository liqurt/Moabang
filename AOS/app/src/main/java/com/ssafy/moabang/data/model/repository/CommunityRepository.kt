package com.ssafy.moabang.data.model.repository

import android.util.Log
import com.ssafy.moabang.config.GlobalApplication
import com.ssafy.moabang.data.api.CommunityApi
import com.ssafy.moabang.data.model.dto.Community
import com.ssafy.moabang.data.model.dto.RecruitCreateRequest
import retrofit2.Response
import java.lang.Exception

class CommunityRepository {
    private val communityApi: CommunityApi = GlobalApplication.retrofit.create(CommunityApi::class.java)

    fun getAllCommunity() : Response<List<Community>>?{
        try {
            val res = communityApi.getAllCommunity().execute()
            return res
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun insertCommunity(requestCreateRequest: RecruitCreateRequest) : Response<Boolean>? {
        try {
            val res = communityApi.insertCommunity(requestCreateRequest).execute()
            return res
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getCommunity(_rid : Int) : Response<Community>?{
        try {
            val res = communityApi.getCommunity(_rid).execute()
            return res
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun updateCommunity(_rid: Int, _recruitCreateRequest : RecruitCreateRequest) : Response<Boolean>?{
        Log.d("BBBBB", "C_U input : ${_rid}, ${_recruitCreateRequest}")
        try {
            val res = communityApi.updateCommunity(_rid, _recruitCreateRequest).execute()
            Log.d("BBBBB", "C_U1 : ${res}")
            return res
        } catch (e: Exception) {
            Log.d("BBBBB", "C_U2 ${e.message}")
            e.printStackTrace()
        }
        return null
    }

    fun deleteCommunity(_rid: Int) : Response<Boolean>?{
        try {
            val res = communityApi.deleteCommunity(_rid,).execute()
            return res
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getLatest5Community() : Response<List<Community>>?{
        try {
            val res = communityApi.getLatest5Community().execute()
            return res
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}