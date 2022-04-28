package com.ssafy.moabang.src.theme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import com.bumptech.glide.Glide
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

        Glide.with(binding.ivThemeDAImg).load(theme.img).centerCrop().into(binding.ivThemeDAImg)

        binding.tvThemeDAGenre.text = theme.genre
        binding.tvThemeDATime.text = theme.time.toString() + "분"
        binding.tvThemeDARating.text = theme.rating.toString()
        binding.tvThemeDADiff.text = theme.difficulty.toString()
        binding.tvThemeDAPlayer.text = theme.player + "명"
        binding.tvThemeDAType.text = theme.type
        binding.tvThemeDAActive.text = theme.active
        binding.tvThemeDADesc.apply {
            text = theme.description
            movementMethod = ScrollingMovementMethod()
        }

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