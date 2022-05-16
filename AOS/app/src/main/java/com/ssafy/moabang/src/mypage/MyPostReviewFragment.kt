package com.ssafy.moabang.src.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.moabang.R
import com.ssafy.moabang.databinding.FragmentMyPostReviewBinding

class MyPostReviewFragment : Fragment() {
    private lateinit var binding: FragmentMyPostReviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPostReviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}