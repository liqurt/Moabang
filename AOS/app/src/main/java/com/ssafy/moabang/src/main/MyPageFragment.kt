package com.ssafy.moabang.src.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.moabang.config.GlobalAuthHelper
import com.ssafy.moabang.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {
    private lateinit var binding: FragmentMyPageBinding
    private lateinit var mainActivity: MainActivity
    private var globalAuthHelper = GlobalAuthHelper()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        // TODO: 받아온 유저 정보로 마이페이지 초기화

        binding.tvMypageFLogout.setOnClickListener {
            globalAuthHelper.accountLogout(requireContext(), mainActivity)
        }

        binding.tvMypageFSignout.setOnClickListener {
            globalAuthHelper.accountSignOut(requireContext(), mainActivity)
        }
    }

}