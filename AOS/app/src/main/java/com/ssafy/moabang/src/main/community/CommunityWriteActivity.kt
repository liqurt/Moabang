package com.ssafy.moabang.src.main.community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.kakao.sdk.user.UserApiClient
import com.ssafy.moabang.R
import com.ssafy.moabang.databinding.ActivityCommunityWriteBinding

class CommunityWriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCommunityWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    fun initView(){
        setUIwithKakao()
    }

    private fun setUIwithKakao(){
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("AAAAA", "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i("AAAAA", "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")

                binding.tvCommuItemFAuthor.text = user.kakaoAccount?.profile?.nickname
                Glide.with(this)
                    .load(user.kakaoAccount?.profile?.thumbnailImageUrl)
                    .into(binding.civCommuItemF)
            }
        }
    }
}