package com.ssafy.moabang.src.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.moabang.R
import com.ssafy.moabang.databinding.ActivityMyLikeBinding

class MyLikeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyLikeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyLikeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        binding.toolbarLike.ivToolbarIcon.setOnClickListener { finish() }
        binding.toolbarLike.tvToolbarTitle.text = "찜한 테마"
    }
}