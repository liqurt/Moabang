package com.ssafy.moabang.src.theme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.ssafy.moabang.R
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.databinding.ActivityThemeDetailBinding

class ThemeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThemeDetailBinding
    private lateinit var theme: Theme
    lateinit var behavior: BottomSheetBehavior<ConstraintLayout>

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
        behavior = BottomSheetBehavior.from(binding.themeDABottomSheet)
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding.bsThemeDA.setImageResource(R.drawable.icon_down)
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.bsThemeDA.setImageResource(R.drawable.icon_up)
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        val frag = ThemeReviewFragment()
        var bundle = Bundle(1)
        bundle.putParcelable("theme", theme)
        frag.arguments = bundle
        setFragment(frag)

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


        binding.bsThemeDA.setOnClickListener {
            if(behavior.state == BottomSheetBehavior.STATE_COLLAPSED){
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else if(behavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        binding.tvThemeDAHomepage.setOnClickListener {
            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })

            val frag = ThemeUrlFragment()
            var bundle = Bundle(1)
            bundle.putString("url", theme.url)
            frag.arguments = bundle
            setFragment(frag)
        }
        binding.tvThemeDAReview.setOnClickListener {
            val frag = ThemeReviewFragment()
            var bundle = Bundle(1)
            bundle.putParcelable("theme", theme)
            frag.arguments = bundle
            setFragment(frag)
        }
        binding.tvThemeDACompare.setOnClickListener {  }
        binding.tvThemeDAReserve.setOnClickListener {  }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout_themeDetail, fragment).commit()
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