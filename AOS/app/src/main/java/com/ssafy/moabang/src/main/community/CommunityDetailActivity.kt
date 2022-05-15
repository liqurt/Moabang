package com.ssafy.moabang.src.main.community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.kakao.sdk.user.UserApiClient
import com.ssafy.moabang.R
import com.ssafy.moabang.config.GlobalApplication.Companion.sp
import com.ssafy.moabang.data.model.dto.Community
import com.ssafy.moabang.data.model.dto.RecruitCreateRequest
import com.ssafy.moabang.data.model.repository.CommunityRepository
import com.ssafy.moabang.databinding.ActivityCommunityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CommunityDetailActivity : AppCompatActivity() {

    private lateinit var mode : String // "write" or "read"
    private lateinit var community: Community
    private lateinit var binding: ActivityCommunityDetailBinding
    private var communityRepository = CommunityRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setMode()
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    private fun setMode(){
        mode = intent.getStringExtra("mode")!!
        if(mode == "read"){
            community = intent.getParcelableExtra("community")!!
        }
    }

    private fun initView() {
        if(mode == "read"){
            binding.rArticleInfo.visibility = View.VISIBLE
            binding.rContent.visibility = View.VISIBLE
            binding.rUDButtons.visibility = View.VISIBLE
            binding.wArticleInfo.visibility = View.GONE
            binding.wContent.visibility = View.GONE
            binding.wCButtons.visibility = View.GONE

            binding.tvCommuItemFAuthor.text = community.user.nickname
            binding.tvCommuItemFContent.text = community.content
            binding.tvCommuItemFHeader.text = community.header
            binding.tvCommuItemFTitle.text = community.title
            Glide.with(this)
                .load(community.user.pimg)
                .placeholder(R.drawable.door)
                .into(binding.civCommuItemF)

            if (isMine()) {
                binding.rUDButtons.visibility = View.VISIBLE
                binding.btCommuItemFRemove.setOnClickListener { removeCommunity() }
                binding.btCommuItemFEdit.setOnClickListener { modeChangeToEdit() }
            } else {
                binding.rUDButtons.visibility = View.GONE
            }
        }else if(mode == "write" || mode == "edit"){
            binding.rArticleInfo.visibility = View.GONE
            binding.rContent.visibility = View.GONE
            binding.rUDButtons.visibility = View.GONE
            binding.wArticleInfo.visibility = View.VISIBLE
            binding.wContent.visibility = View.VISIBLE
            binding.wCButtons.visibility = View.VISIBLE

            ArrayAdapter.createFromResource(this, R.array.community_header, android.R.layout.simple_spinner_item).also {
                    adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spCommuItemFHeader.adapter = adapter
            }

            if(mode == "write"){
                binding.btCommuWriteWrite.setOnClickListener { write() }
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e("AAAAA", "사용자 정보 요청 실패", error)
                    }
                    else if (user != null) {
                        binding.tvCommuItemFAuthor.text = user.kakaoAccount?.profile?.nickname
                        Glide.with(this)
                            .load(user.kakaoAccount?.profile?.thumbnailImageUrl)
                            .into(binding.civCommuItemF)
                    }
                }
            }else if (mode == "edit"){
                binding.etCommuItemFTitle.setText(binding.tvCommuItemFTitle.text)
                binding.etCommuItemFContent.setText(binding.tvCommuItemFContent.text)
                binding.btCommuWriteWrite.setOnClickListener { edit() }
            }


        }


    }

    private fun modeChangeToEdit() {
        mode = "edit"
        initView()
    }

    private fun removeCommunity() {
        CoroutineScope(Dispatchers.IO).launch {
            communityRepository.deleteCommunity(community.rid)
            finish()
        }
    }

    private fun write(){
        CoroutineScope(Dispatchers.IO).launch {
            val header = binding.spCommuItemFHeader.selectedItem.toString()
            val title = binding.etCommuItemFTitle.text.toString()
            val content = binding.etCommuItemFContent.text.toString()
            val recruitCreateRequest = RecruitCreateRequest(content, header, title)
            val result = communityRepository.insertCommunity(recruitCreateRequest)
            finish()
        }
    }

    private fun edit(){
        Log.d("BBBBB", "edit: ")
        CoroutineScope(Dispatchers.IO).launch {
            val header = binding.spCommuItemFHeader.selectedItem.toString()
            val title = binding.etCommuItemFTitle.text.toString()
            val content = binding.etCommuItemFContent.text.toString()
            val rid = community.rid
            val recruitCreateRequest = RecruitCreateRequest(content, header, title)
            val result = communityRepository.updateCommunity(rid, recruitCreateRequest)
            finish()
        }
    }

    private fun isMine(): Boolean {
        return community.user.uid == sp.getInt("uid")
    }
}