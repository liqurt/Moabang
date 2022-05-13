package com.ssafy.moabang.src.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.moabang.R
import com.ssafy.moabang.databinding.ActivityMyDoneBinding

class MyDoneActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyDoneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyDoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        binding.toolbarDone.ivToolbarIcon.setOnClickListener { finish() }
        binding.toolbarDone.tvToolbarTitle.text = "이용한 테마"
    }
}