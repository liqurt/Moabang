package com.ssafy.moabang.config

import android.content.Context
import com.nhn.android.naverlogin.OAuthLogin

import com.nhn.android.naverlogin.data.OAuthLoginState
import com.ssafy.moabang.src.login.NaverLogin.DeleteTokenTask
import com.ssafy.moabang.src.main.MainActivity
import java.lang.Exception


class GlobalAuthHelper {
    fun accountLogout(context: Context?, activity: MainActivity) {
        if (OAuthLoginState.OK == OAuthLogin.getInstance().getState(context)) {
            OAuthLogin.getInstance().logout(context)
            activity.directToLoginActivity(true)
        }
    }

    fun accountSignOut(context: Context, activity: MainActivity) {
        if (OAuthLoginState.OK == OAuthLogin.getInstance().getState(context)) {
            // 네이버 연동 해제
            try {
                val deleteTokenTask = DeleteTokenTask(context, activity)
                deleteTokenTask.execute(context).get()
            } catch (e: Exception) {
                e.printStackTrace()
                activity.directToLoginActivity(false)
            }
        }
    }
}