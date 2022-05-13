package com.ssafy.moabang.src.main.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.moabang.R
import com.ssafy.moabang.databinding.FragmentCommunityItemBinding

class CommunityItemFragment : Fragment() {

    private lateinit var binding : FragmentCommunityItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunityItemBinding.inflate(inflater, container, false)
        return binding.root
    }

}