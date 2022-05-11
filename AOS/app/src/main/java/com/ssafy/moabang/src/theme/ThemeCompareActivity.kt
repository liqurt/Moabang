package com.ssafy.moabang.src.theme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.moabang.R
import com.ssafy.moabang.adapter.CompareThemeListRVAdapter
import com.ssafy.moabang.adapter.CompareTitleListRVAdapter
import com.ssafy.moabang.data.model.dto.ThemeForCompare
import com.ssafy.moabang.data.model.repository.Repository
import com.ssafy.moabang.databinding.ActivityThemeCompareBinding
import com.ssafy.moabang.src.util.CompareList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ThemeCompareActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThemeCompareBinding
    private lateinit var compareTitleListRVAdapter: CompareTitleListRVAdapter
    lateinit var compareThemeListRVAdapter: CompareThemeListRVAdapter

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

        compareThemeListRVAdapter = CompareThemeListRVAdapter()
//        compareThemeListRVAdapter.data = CompareList.clist

        CompareList.clistLiveData.observe(this){
            compareThemeListRVAdapter.data = it as MutableList<ThemeForCompare>
            Log.d("THEME COMPARE TEST", "setRVAdapter: $it")
            compareThemeListRVAdapter.notifyDataSetChanged()
        }

        binding.rvThemeCompare.apply {
            layoutManager = LinearLayoutManager(this@ThemeCompareActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = compareThemeListRVAdapter
        }
        
        compareTitleListRVAdapter.itemClickListener = object : CompareTitleListRVAdapter.ItemClickListener {
            override fun onClick(item: ThemeForCompare) {
                CompareList.addTheme(item)
            }
        }

    }
}