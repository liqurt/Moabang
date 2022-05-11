package com.ssafy.moabang.src.theme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.moabang.R
import com.ssafy.moabang.databinding.ActivityThemeCompareBinding

class ThemeCompareActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThemeCompareBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeCompareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        binding.toolbarThemeCompare.ivToolbarIcon.setOnClickListener { finish() }
        binding.toolbarThemeCompare.tvToolbarTitle.text = "테마 비교하기"
    }
}