package com.ssafy.moabang.src.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.ssafy.moabang.databinding.ActivityLoginBinding
import com.ssafy.moabang.src.main.MainActivity
import androidx.activity.viewModels
import com.nhn.android.naverlogin.OAuthLogin
import com.ssafy.moabang.BuildConfig
import com.ssafy.moabang.config.GlobalApplication
import android.widget.Toast

import com.nhn.android.naverlogin.OAuthLoginHandler
import org.json.JSONException

import org.json.JSONObject

import android.os.AsyncTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val model: KakaoLoginViewModel by viewModels()
    private lateinit var mOAuthLoginModule:OAuthLogin

    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("AAAAA", "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i("AAAAA", "카카오계정으로 로그인 성공 ${token.accessToken}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.activity = this

        init()

        // 로그인 구현 전이라 우선 요 버튼 누르면 메인 화면으로 넘어가게 구현
        binding.tvLoginATmp.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun init(){
        mOAuthLoginModule = OAuthLogin.getInstance()
        mOAuthLoginModule.init(this, BuildConfig.naver_client_id, BuildConfig.naver_client_secret, "모아방")
    }

    fun naverLogin(view: View) {
        mOAuthLoginModule.startOauthLoginActivity(
            this,
            mOAuthLoginHandler
        )
    }

    fun kakaoLogin(view: View) {
        Log.d("AAAAA", "kakaoLogin")
        val context = this
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e("AAAAA", "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i("AAAAA", "카카오톡으로 로그인 성공 ${token.accessToken}")
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    private val mOAuthLoginHandler: OAuthLoginHandler = @SuppressLint("HandlerLeak")
    object : OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if (success) {
                val accessToken: String =
                    mOAuthLoginModule.getAccessToken(this@LoginActivity)
                val refreshToken: String =
                    mOAuthLoginModule.getRefreshToken(this@LoginActivity)
                val expiresAt: Long =
                    mOAuthLoginModule.getExpiresAt(this@LoginActivity)
                val tokenType: String =
                    mOAuthLoginModule.getTokenType(this@LoginActivity)
                CoroutineScope(Dispatchers.Main).launch{
                    requestApiTask(this@LoginActivity, mOAuthLoginModule)
                }
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            } else {
                val errorCode: String =
                    mOAuthLoginModule.getLastErrorCode(this@LoginActivity).code
                val errorDesc: String =
                    mOAuthLoginModule.getLastErrorDesc(this@LoginActivity)
                Toast.makeText(
                    this@LoginActivity, "errorCode:" + errorCode
                            + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun requestApiTask(mContext: Context, mOAuthLoginModule: OAuthLogin) {

        val mContext: Context = mContext
        val mOAuthLoginModule: OAuthLogin = mOAuthLoginModule

        val url = "https://openapi.naver.com/v1/nid/me"
        var res: String? = null
        Thread {
            val at: String = mOAuthLoginModule.getAccessToken(mContext)
            res = mOAuthLoginModule.requestApi(mContext, at, url)
        }.start()


        if(res != null) {
            Log.d("NAVER_LOGIN", "requestApiTask: API CALLED")
            try {
                val loginResult = JSONObject(res)
                if (loginResult.getString("resultcode") == "00") {
                    val response = loginResult.getJSONObject("response")
                    val id = response.getString("id")
                    val email = response.getString("email")
                    Toast.makeText(mContext, "id : $id email : $email", Toast.LENGTH_SHORT).show()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }
}

