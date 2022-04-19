package com.ssafy.moabang.src.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.moabang.databinding.FragmentCafeBinding


class CafeFragment : Fragment() {
    private lateinit var binding: FragmentCafeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCafeBinding.inflate(inflater, container, false)
        return binding.root
    }

}