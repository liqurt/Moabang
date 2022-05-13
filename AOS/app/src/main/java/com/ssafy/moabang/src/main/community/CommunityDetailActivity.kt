package com.ssafy.moabang.src.main.community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.ssafy.moabang.R
import com.ssafy.moabang.data.model.dto.Community
import com.ssafy.moabang.databinding.ActivityCommunityDetailBinding

class CommunityDetailActivity : AppCompatActivity() {

    private lateinit var community : Community
    private lateinit var binding : ActivityCommunityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        community = intent.getParcelableExtra("community")!!
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    private fun initView() {
        binding.tvCommuItemFAuthor.text = community.user.nickname
        binding.tvCommuItemFContent.text = community.content
        binding.tvCommuItemFHeader.text = community.header
        binding.tvCommuItemFTitle.text = community.title
        Glide.with(this).load(community.user.pimg).into(binding.civCommuItemF)
    }
}