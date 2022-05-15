package com.ssafy.moabang.data.model.repository

import android.util.Log
import com.ssafy.moabang.config.GlobalApplication
import com.ssafy.moabang.data.api.CommunityApi
import com.ssafy.moabang.data.model.dto.Community
import retrofit2.Response
import java.lang.Exception

class CommunityRepository {
    private val communityApi: CommunityApi = GlobalApplication.retrofit.create(CommunityApi::class.java)

    fun getAllCommunity() : Response<List<Community>>?{
        try {
            val res = communityApi.getAllCommunity().execute()
            Log.d("AAAAA", "CommunityRepository_getAllCommunity ${res.body()}")
            return res
        } catch (e: Exception) {
            Log.d("AAAAA", "CommunityRepository_getAllCommunity ${e.message}")
            e.printStackTrace()
        }
        return null
    }

    fun insertCommunity(_header : String, _title: String, _content : String ) : Response<Boolean>? {
        try {
            val res = communityApi.insertCommunity(_header,_title,_content).execute()
            Log.d("BBBBB", "CommunityRepository_insertCommunity${res.body()}")
            return res
        } catch (e: Exception) {
            Log.d("BBBBB", "CommunityRepository_insertCommunity ${e.message}")
            e.printStackTrace()
        }
        return null
    }

    fun getCommunity(_rid : Int) : Response<Community>?{
        try {
            val res = communityApi.getCommunity(_rid).execute()
            Log.d("AAAAA", "CommunityRepository_getCommunity${res.body()}")
            return res
        } catch (e: Exception) {
            Log.d("AAAAA", "CommunityRepository_getCommunity ${e.message}")
            e.printStackTrace()
        }
        return null
    }

    fun updateCommunity(_rid: Int, _header : String, _title: String, _content : String ) : Response<Boolean>?{
        try {
            val res = communityApi.updateCommunity(_rid, _header, _title,_content).execute()
            Log.d("AAAAA", "CommunityRepository_updateCommunity${res.body()}")
            return res
        } catch (e: Exception) {
            Log.d("AAAAA", "CommunityRepository_updateCommunity ${e.message}")
            e.printStackTrace()
        }
        return null
    }

    fun deleteCommunity(_rid: Int) : Response<Boolean>?{
        try {
            val res = communityApi.deleteCommunity(_rid,).execute()
            Log.d("AAAAA", "CommunityRepository_deleteCommunity${res.body()}")
            return res
        } catch (e: Exception) {
            Log.d("AAAAA", "CommunityRepository_deleteCommunity ${e.message}")
            e.printStackTrace()
        }
        return null
    }

    fun getLatest5Community() : Response<List<Community>>?{
        try {
            val res = communityApi.getLatest5Community().execute()
            Log.d("AAAAA", "CommunityRepository_getLatest5Community${res.body()}")
            return res
        } catch (e: Exception) {
            Log.d("AAAAA", "CommunityRepository_getLatest5Community ${e.message}")
            e.printStackTrace()
        }
        return null
    }

}