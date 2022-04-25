package com.ssafy.moabang.util

import android.content.Context
import android.content.SharedPreferences
import com.ssafy.moabang.config.GlobalApplication

class SharedPreferencesUtil(context: Context) {
    private var preferences: SharedPreferences = context.getSharedPreferences(GlobalApplication.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun addUserCookie(cookies: HashSet<String>) {
        val editor = preferences.edit()
        editor.putStringSet(GlobalApplication.COOKIES_KEY_NAME, cookies)
        editor.apply()
    }

    fun getUserCookie(): MutableSet<String>? {
        return preferences.getStringSet(GlobalApplication.COOKIES_KEY_NAME, HashSet())
    }

    fun setString(key: String, value: String) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key:String): String? {
        return preferences.getString(key, null)
    }

    fun deleteString(key: String) {
        val editor = preferences.edit()
        editor.remove(key).apply()
    }
}