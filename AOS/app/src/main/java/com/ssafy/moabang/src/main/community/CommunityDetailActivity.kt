package com.ssafy.moabang.src.main.community

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.ssafy.moabang.R
import com.ssafy.moabang.config.GlobalApplication.Companion.sp
import com.ssafy.moabang.data.model.dto.Community
import com.ssafy.moabang.databinding.ActivityCommunityDetailBinding

private val TAG = "CommunityDetailActivity"

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

        if (isMine()){
            binding.UDButtons.visibility = View.VISIBLE
            binding.btCommuItemFRemove.setOnClickListener { removeCommunity() }
            binding.btCommuItemFEdit.setOnClickListener { editCommunity() }
        }else{
            binding.UDButtons.visibility = View.GONE
        }
    }

    private fun editCommunity() {
        Toast.makeText(this, "편집 미구현", Toast.LENGTH_SHORT).show()
    }

    private fun removeCommunity() {
        Toast.makeText(this, "제거 미구현", Toast.LENGTH_SHORT).show()
    }

    private fun isMine() : Boolean{
        return community.user.uid == sp.getInt("uid")
    }
}