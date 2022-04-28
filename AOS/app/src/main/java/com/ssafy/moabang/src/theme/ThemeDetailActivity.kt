package com.ssafy.moabang.src.theme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.moabang.R
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.databinding.ActivityThemeDetailBinding

class ThemeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThemeDetailBinding
    private lateinit var theme: Theme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getParcelableExtra<Theme>("theme")?.let {
            theme = it
        } ?: finish()

        init()

    }

    private fun init(){
        setThemeInfo()
        setClickListener()
    }

    private fun setThemeInfo() {
        binding.tvToolbarTheme.text = theme.name
        binding.tvToolbarCafe.text = theme.cafeName

        setLike()

    }

    private fun setClickListener(){
        binding.ivToolbarLeadingIcon.setOnClickListener {
            finish()
        }
    }

    private fun setLike(){
        if(theme.like) {
            binding.ivToolbarTrailingIcon.setImageResource(R.drawable.icon_like_after)
        } else {
            binding.ivToolbarTrailingIcon.setImageResource(R.drawable.icon_like_before)
        }

        binding.ivToolbarTrailingIcon.setOnClickListener {
            theme.like = !theme.like
            if(theme.like) {
                binding.ivToolbarTrailingIcon.setImageResource(R.drawable.icon_like_after)
            } else {
                binding.ivToolbarTrailingIcon.setImageResource(R.drawable.icon_like_before)
            }
        }
    }
}