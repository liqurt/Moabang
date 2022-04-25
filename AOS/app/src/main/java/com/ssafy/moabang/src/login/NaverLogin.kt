package com.ssafy.moabang.src.login

import android.app.Activity
import android.content.Context
import com.nhn.android.naverlogin.OAuthLogin

import android.os.AsyncTask

import android.content.Intent

import org.json.JSONObject

import com.nhn.android.naverlogin.data.OAuthLoginState

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ssafy.moabang.config.GlobalAuthHelper
import com.ssafy.moabang.data.model.dto.UserInfo
import com.ssafy.moabang.src.main.MainActivity
import com.ssafy.moabang.util.LoginUtil
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL


class NaverLogin: Activity() {
    val NAVER_RESPONSE_CODE = "00" // 정상 반환 시 코드

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mNaverLoginModule = OAuthLogin.getInstance()
        val accessToken = mNaverLoginModule.getAccessToken(this)
        Log.d("NAV_LOGIN_TAG", "onCreate: $accessToken")
        if (accessToken != null && OAuthLoginState.OK == mNaverLoginModule.getState(this)) {
            val reqNaverUserInfo = ReqNHNUserInfo()
            reqNaverUserInfo.execute(accessToken)
        } else {
            val tokenRefresh = RefreshNHNToken()
            try {
                tokenRefresh.execute().get()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val reqNaverUserInfo = ReqNHNUserInfo()
            reqNaverUserInfo.execute(mNaverLoginModule.getAccessToken(this))
        }
    }

    inner class RefreshNHNToken : AsyncTask<Void?, Void?, Boolean>() {
        override fun doInBackground(vararg p0: Void?): Boolean? {
            try {
                val mNaverLoginModule = OAuthLogin.getInstance()
                mNaverLoginModule.refreshAccessToken(applicationContext)
            } catch (e: Exception) {
                Log.e("Error RefreshNHNToken", e.toString())
            }
            return true
        }
    }

    inner class ReqNHNUserInfo : AsyncTask<String?, Void?, String?>() {
        var result: String? = null
        override fun doInBackground(vararg p0: String?): String? {
            val token = p0[0] // 네이버 로그인 접근 토큰
            val header = "Bearer $token" // Bearer 다음에 공백 추가
            try {
                val apiURL = "https://openapi.naver.com/v1/nid/me"
                val url = URL(apiURL)
                val con: HttpURLConnection = url.openConnection() as HttpURLConnection
                con.requestMethod = "GET"
                con.setRequestProperty("Authorization", header)
                val responseCode: Int = con.responseCode
                val br: BufferedReader
                if (responseCode == 200) { // 정상 호출
                    br = BufferedReader(InputStreamReader(con.inputStream))
                } else {  // 에러 발생
                    br = BufferedReader(InputStreamReader(con.errorStream))
                }
                var inputLine: String?
                val response = StringBuilder()
                while (br.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                    Log.d("RESPONSE_INFO", "doInBackground: $inputLine")
                }
                result = response.toString()
                br.close()
                Log.d("ReqNHNUserInfo Response", result!!)
            } catch (e: Exception) {
                Log.e("Error ReqNHNUserInfo", e.toString())
            }
            return result
        }

        override fun onPostExecute(s: String?) {
            super.onPostExecute(s)
            try {
                val `object` = JSONObject(result)
                if (`object`.getString("resultcode") == NAVER_RESPONSE_CODE) {
                    val jsonObject = JSONObject(`object`.getString("response"))
                    var userInfo = UserInfo("NAVER", jsonObject.getString("id"), jsonObject.getString("email"), jsonObject.getString("name"), jsonObject.getString("profile_image"), ArrayList(), ArrayList(), ArrayList())
                    LoginUtil.saveUserInfo(userInfo)
                    Log.d("USER_INFO", "onPostExecute: ${userInfo.toString()}")
                    val intent = Intent(this@NaverLogin, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@NaverLogin, "로그인 실패", Toast.LENGTH_SHORT).show()
                    val mGlobalAuthHelper = GlobalAuthHelper()
                    mGlobalAuthHelper.accountSignOut(this@NaverLogin, MainActivity())
                    finish()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    internal class DeleteTokenTask(mContext: Context, mActivity: MainActivity) : AsyncTask<Context?, Void?, Boolean>() {
        var context: Context
        var mainActivity: MainActivity
        override fun doInBackground(vararg p0: Context?): Boolean? {
            return OAuthLogin.getInstance().logoutAndDeleteToken(p0[0])
        }

        override fun onPostExecute(result: Boolean) {
            super.onPostExecute(result)
            mainActivity.directToLoginActivity(result)
        }

        init {
            context = mContext
            mainActivity = mActivity
        }
    }
}