package com.ssafy.moabang.src.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.moabang.R
import com.ssafy.moabang.databinding.ActivityLoginBinding
import com.ssafy.moabang.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}