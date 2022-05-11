package com.ssafy.moabang.src.theme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.ssafy.moabang.R
import com.ssafy.moabang.config.GlobalApplication.Companion.sp
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.data.model.repository.Repository
import com.ssafy.moabang.data.model.viewmodel.ThemeViewModel
import com.ssafy.moabang.databinding.ActivityThemeDetailBinding
import com.ssafy.moabang.src.main.ThemeFragment
import com.ssafy.moabang.src.main.cafe.CafeDetailActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ThemeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThemeDetailBinding
    private lateinit var theme: Theme
    lateinit var behavior: BottomSheetBehavior<ConstraintLayout>
    private val themeViewModel: ThemeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("token", "onCreate token : ${sp.getString("moabangToken")}")
        intent.getParcelableExtra<Theme>("theme")?.let {
            theme = it
            init()
        } ?: finish()
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
        binding.tvToolbarTheme.text = theme.tname
        binding.tvToolbarCafe.text = theme.cname

        Glide.with(binding.ivThemeDAImg).load(theme.img).centerCrop().into(binding.ivThemeDAImg)

        binding.tvThemeDAGenre.text = theme.genre
        binding.tvThemeDATime.text = theme.time
        binding.tvThemeDARating.text = theme.grade.toString()
        binding.tvThemeDADiff.text = theme.difficulty.toString()
        binding.tvThemeDAPlayer.text = theme.rplayer + "명"
        binding.tvThemeDAType.text = theme.type
        binding.tvThemeDAActive.text = if(theme.activity == "") "정보없음" else theme.activity
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

        binding.tvThemeDACompare.setOnClickListener {
            startActivity(Intent(this, ThemeCompareActivity::class.java))
        }

        binding.tvThemeDACafe.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val cafe = Repository.get().getCafe(theme.cid)
                startActivity(Intent(this@ThemeDetailActivity, CafeDetailActivity::class.java)
                    .putExtra("cafe", cafe))
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout_themeDetail, fragment).commit()
    }

    private fun setLike(){
        if(theme.islike) {
            binding.ivToolbarTrailingIcon.setImageResource(R.drawable.icon_like_after)
        } else {
            binding.ivToolbarTrailingIcon.setImageResource(R.drawable.icon_like_before)
        }

        binding.ivToolbarTrailingIcon.setOnClickListener {
            themeViewModel.themeLike(theme.tid)
            theme.islike = !theme.islike
            if(theme.islike) {
                binding.ivToolbarTrailingIcon.setImageResource(R.drawable.icon_like_after)
            } else {
                binding.ivToolbarTrailingIcon.setImageResource(R.drawable.icon_like_before)
            }
        }
    }

}