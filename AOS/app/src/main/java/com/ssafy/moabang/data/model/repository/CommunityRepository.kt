package com.ssafy.moabang.data.model.repository

import android.util.Log
import com.ssafy.moabang.config.GlobalApplication
import com.ssafy.moabang.data.api.CommunityApi
import com.ssafy.moabang.data.model.dto.Community
import retrofit2.Response
import java.lang.Exception

class CommunityRepository {
    val communityApi: CommunityApi = GlobalApplication.retrofit.create(CommunityApi::class.java)

    fun getLatest5Community() : Response<List<Community>>?{
        try {
            val res = communityApi.getLatest5Community().execute()
            Log.d("AAAAA", "RecruitRepository_getLatest5Community${res.body()}")
            return res
        } catch (e: Exception) {
            Log.d("AAAAA", "RecruitRepository_getLatest5Community ${e.message}")
            e.printStackTrace()
        }
        return null
    }
}