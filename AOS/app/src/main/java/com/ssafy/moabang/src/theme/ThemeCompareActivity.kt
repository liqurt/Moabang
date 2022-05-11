package com.ssafy.moabang.src.theme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.moabang.R
import com.ssafy.moabang.adapter.CompareTitleListRVAdapter
import com.ssafy.moabang.databinding.ActivityThemeCompareBinding
import com.ssafy.moabang.src.util.CompareList

class ThemeCompareActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThemeCompareBinding
    private lateinit var compareTitleListRVAdapter: CompareTitleListRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeCompareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        binding.toolbarThemeCompare.ivToolbarIcon.setOnClickListener { finish() }
        binding.toolbarThemeCompare.tvToolbarTitle.text = "테마 비교하기"

        if(CompareList.items.isNotEmpty()) binding.tvTcbsBlank.visibility = View.GONE

        setRVAdapter()
    }

    private fun setRVAdapter(){
        compareTitleListRVAdapter = CompareTitleListRVAdapter()
        compareTitleListRVAdapter.data = CompareList.items

        binding.rvTcbs.apply {
            layoutManager = LinearLayoutManager(this@ThemeCompareActivity, LinearLayoutManager.VERTICAL, false)
            adapter = compareTitleListRVAdapter
        }

    }
}