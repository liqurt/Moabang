package com.ssafy.moabang.src.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.moabang.R
import com.ssafy.moabang.databinding.FragmentMyPostCommunityBinding

class MyPostCommunityFragment : Fragment() {
    private lateinit var binding: FragmentMyPostCommunityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPostCommunityBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}