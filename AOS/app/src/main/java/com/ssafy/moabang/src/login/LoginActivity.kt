package com.ssafy.moabang.src.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.moabang.R
import com.ssafy.moabang.databinding.ActivityLoginBinding
import com.ssafy.moabang.databinding.ActivityMainBinding
import com.ssafy.moabang.src.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 로그인 구현 전이라 우선 요 버튼 누르면 메인 화면으로 넘어가게 구현
        binding.tvLoginATmp.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}