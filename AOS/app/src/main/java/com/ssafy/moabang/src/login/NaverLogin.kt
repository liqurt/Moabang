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
import androidx.test.core.app.ApplicationProvider
import com.ssafy.moabang.config.GlobalApplication
import com.ssafy.moabang.src.main.MainActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL


class NaverLogin: Activity() {
    val NAVER_RESPONSE_CODE = "00" // 정상 반환 시 코드
    val NAVER_JSON_KEY = arrayOf("id")

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

    internal class RefreshNHNToken : AsyncTask<Void?, Void?, Boolean>() {
        override fun doInBackground(vararg p0: Void?): Boolean? {
            try {
                val mNaverLoginModule = OAuthLogin.getInstance()
                mNaverLoginModule.refreshAccessToken(ApplicationProvider.getApplicationContext<Context>())
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
                    val userInfo: MutableList<String> = ArrayList()
                    val jsonObject = JSONObject(`object`.getString("response"))
                    userInfo.add(
                        String.format(
                            "%s-%s",
                            "NAVER",
                            jsonObject.getString(NAVER_JSON_KEY.get(0))
                        )
                    )
                    val mGlobalHelper = GlobalApplication()
                    mGlobalHelper.setGlobalUserLoginInfo(userInfo)
                    val intent = Intent(this@NaverLogin, MainActivity::class.java)
                    startActivity(intent)
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
//            mainActivity.directToMainActivity(result)
        }

        init {
            context = mContext
            mainActivity = mActivity
        }
    }
}