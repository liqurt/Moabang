package com.ssafy.moabang.src.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.ssafy.moabang.databinding.ActivityLoginBinding
import com.ssafy.moabang.src.main.MainActivity
import androidx.activity.viewModels
import com.ssafy.moabang.config.GlobalApplication
import com.ssafy.moabang.src.retrofitInterface.loginService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val model: KakaoLoginViewModel by viewModels()

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

        // 로그인 구현 전이라 우선 요 버튼 누르면 메인 화면으로 넘어가게 구현
        binding.tvLoginATmp.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    fun kakaoLogin(view : View) {
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
                    sendTokenToBackend(token)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    /**
     * 카카오톡 로그인 성공 후 토큰을 서버에 전송한다.
     */
    fun sendTokenToBackend(token: OAuthToken) {
        Log.d("AAAAA","kakaoToken : ${token.accessToken}")
        val loginService = GlobalApplication.retrofit.create(loginService::class.java)
        loginService.login(token.accessToken).enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    Log.d("AAAAA","${response.body()}") // true == 로그인 성공
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Log.e("AAAAA","네트워킹 성공, 하지만 원하는 결과가 아님. ${response.errorBody()}")
                    Toast.makeText(this@LoginActivity, "네트워크 성공,  : ${response.errorBody()}", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "네트워크 오류 : ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
}