package com.ssafy.moabang.src.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ssafy.moabang.R
import com.ssafy.moabang.databinding.ActivityMainBinding
import android.content.Intent

import android.widget.Toast
import com.ssafy.moabang.src.login.LoginActivity


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val userInfo: List<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        // setFragment만 설정하면 하단 아이콘?은 안바뀜. selectedItemId도 같이 정해줘야함
        setFragment(HomeFragment())
        binding.bottomNavMain.selectedItemId = R.id.nav_home

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

    fun directToLoginActivity(result: Boolean) {
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        if (result) {
            Toast.makeText(applicationContext, "성공!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "실패! 다시 로그인해주세요.", Toast.LENGTH_SHORT).show()
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }
}