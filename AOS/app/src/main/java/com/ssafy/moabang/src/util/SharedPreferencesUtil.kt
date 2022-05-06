package com.ssafy.moabang.src.util

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class SharedPreferencesUtil(context: Context) {
    private var sp: SharedPreferences = context.getSharedPreferences("moabang", AppCompatActivity.MODE_PRIVATE)

    fun getString(key: String): String?{
        return sp.getString(key, null)
    }
}