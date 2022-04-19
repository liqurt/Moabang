package com.ssafy.moabang.src.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ssafy.moabang.R
import com.ssafy.moabang.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){


        binding.bottomNavMain.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_cafe -> {
                    setFragment(CafeFragment())
                    true
                }
                R.id.nav_theme -> {
                    setFragment(ThemeFragment())
                    true
                }
                R.id.nav_home -> {
                    setFragment(HomeFragment())
                    true
                }
                R.id.nav_community -> {
                    setFragment(CommunityFragment())
                    true
                }
                R.id.nav_mypage -> {
                    setFragment(MyPageFragment())
                    true
                }
                else -> false
            }
        }

    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout_main_container, fragment).commit()
    }
}