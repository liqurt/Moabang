package com.ssafy.moabang.src.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.moabang.R
import com.ssafy.moabang.databinding.ActivityMyPostBinding

class MyPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        binding.toolbarMyPost.ivToolbarIcon.setOnClickListener { finish() }
        binding.toolbarMyPost.tvToolbarTitle.text = "작성글 관리"


    }
}