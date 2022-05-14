package com.ssafy.moabang.src.main.community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.kakao.sdk.user.UserApiClient
import com.ssafy.moabang.R
import com.ssafy.moabang.data.model.repository.CommunityRepository
import com.ssafy.moabang.databinding.ActivityCommunityWriteBinding

class CommunityWriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCommunityWriteBinding
    private var communityRepository = CommunityRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    private fun initView(){
        setUIWithKakao()
        setHeaders()
        binding.btCommuWriteWrite.setOnClickListener {write()}
    }

    private fun write(){
        val header = binding.spCommuItemFHeader.selectedItem.toString()
        val title = binding.etCommuItemFTitle.text.toString()
        val content = binding.etCommuItemFContent.text.toString()

        Log.d("BBBBB","$header, $title, $content")

        val result = communityRepository.insertCommunity(header, title, content)
        Log.d("BBBBB","$result")
        Toast.makeText(this, "글 작성 완료",Toast.LENGTH_SHORT).show()
    }

    private fun setHeaders(){
        ArrayAdapter.createFromResource(this, R.array.community_header, android.R.layout.simple_spinner_item).also {
            adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spCommuItemFHeader.adapter = adapter
        }
    }


    private fun setUIWithKakao(){
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
    }
}