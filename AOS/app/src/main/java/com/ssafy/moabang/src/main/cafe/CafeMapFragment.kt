package com.ssafy.moabang.src.main.cafe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.moabang.databinding.FragmentCafeMapBinding

class CafeMapFragment : Fragment() {
    private lateinit var binding: FragmentCafeMapBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCafeMapBinding.inflate(inflater, container, false)
        return binding.root
    }
}