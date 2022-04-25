package com.ssafy.moabang.util

import android.content.Intent
import com.ssafy.moabang.config.GlobalApplication
import com.ssafy.moabang.data.model.dto.UserInfo
import com.ssafy.moabang.src.login.LoginActivity

object LoginUtil {
    val LOGIN_TYPE = "loginType"
    val USER_EMAIL = "userEmail"
    val USER_ID = "userId"
    val USER_NAME = "userName"
    val IMAGE_URL = "imageUrl"
    val PREF_AREA = "prefArea"
    val PREF_GENRE = "prefGenre"
    val PREF_DIFF = "prefDifficulty"


    private val preferences = GlobalApplication.sSharedPreferences

    fun signOut() {
        preferences.deleteString(GlobalApplication.JWT)
        deleteUserInfo()
    }

    fun saveUserInfo(userInfo: UserInfo) {
        preferences.setString(LOGIN_TYPE, userInfo.loginType)
        preferences.setString(USER_NAME, userInfo.name)
        preferences.setString(USER_EMAIL, userInfo.email)
        preferences.setString(USER_ID, userInfo.userId)
        preferences.setString(IMAGE_URL, userInfo.imageUrl)

    }

    fun deleteUserInfo() {
        preferences.deleteString(LOGIN_TYPE)
        preferences.deleteString(USER_NAME)
        preferences.deleteString(USER_EMAIL)
        preferences.deleteString(USER_ID)
        preferences.deleteString(IMAGE_URL)
        preferences.deleteString(PREF_AREA)
        preferences.deleteString(PREF_GENRE)
        preferences.deleteString(PREF_DIFF)
    }

    fun getUserInfo(): UserInfo? {
        val loginType = preferences.getString(LOGIN_TYPE)
        val userId = preferences.getString(USER_ID)
        val userEmail = preferences.getString(USER_EMAIL)
        val userName = preferences.getString(USER_NAME)
        val imageUrl = preferences.getString(IMAGE_URL)
        val prefArea = ArrayList<String>()
        val prefGenre = ArrayList<String>()
        val prefDiff = ArrayList<String>()

        return if (userEmail.isNullOrBlank() or userName.isNullOrBlank() or (userId == null)) {
            null
        } else {
            UserInfo(loginType!!, userId!!, userEmail!!, userName!!, imageUrl!!, prefArea, prefGenre, prefDiff)
        }
    }
}