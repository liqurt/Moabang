package com.ssafy.moabang.src.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.moabang.adapter.ThemeListRVAdapter
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.databinding.FragmentThemeBinding
import com.ssafy.moabang.src.theme.ThemeDetailActivity

class ThemeFragment : Fragment() {
    private lateinit var binding: FragmentThemeBinding
    private lateinit var themeListRVAdapter: ThemeListRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThemeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        themeListRVAdapter = ThemeListRVAdapter()
        binding.rvThemeF.apply{
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = themeListRVAdapter
        }
        val imgUrl = "http://www.code-k.co.kr/upload_file/thema/%EA%BC%AC%EB%A0%88%EC%95%84%20%EC%9A%B0%EB%9D%BC(1).jpg"
        val imgUrl2 = "http://solver-gd.com/upload_file/room/12344(17).jpg"

        themeListRVAdapter.data = listOf(
            Theme(1, "코드케이 홍대점", "꼬레아 우라", imgUrl, "설명설명", 28000, 4, "2~4", 75, "판타지", 4.6, false, "보통", "복합"),
            Theme(2, "솔버 2호점", "디어 마르시", imgUrl2, "설명설명", 24000, 4, "2~4", 75, "감성", 4.1, true, "적음","자물쇠"),
            Theme(1, "코드케이 홍대점", "꼬레아 우라", imgUrl, "설명설명", 28000, 4, "2~4", 75, "판타지", 4.6, false, "보통","복합"),
            Theme(1, "코드케이 홍대점", "꼬레아 우라", imgUrl, "설명설명", 28000, 4, "2~4", 75, "판타지", 4.6, false, "보통","복합"),
            Theme(1, "코드케이 홍대점", "꼬레아 우라", imgUrl, "설명설명", 28000, 4, "2~4", 75, "판타지", 4.6, false, "보통","복합"),
            Theme(1, "코드케이 홍대점", "꼬레아 우라", imgUrl, "설명설명", 28000, 4, "2~4", 75, "판타지", 4.6, false, "보통","복합"),
            Theme(1, "코드케이 홍대점", "꼬레아 우라", imgUrl, "설명설명", 28000, 4, "2~4", 75, "판타지", 4.6, false,"보통", "복합"),
            Theme(1, "코드케이 홍대점", "꼬레아 우라", imgUrl, "설명설명", 28000, 4, "2~4", 75, "판타지", 4.6, false, "보통","복합"),
            Theme(1, "코드케이 홍대점", "꼬레아 우라", imgUrl, "설명설명", 28000, 4, "2~4", 75, "판타지", 4.6, false, "보통","복합"),
        )

        themeListRVAdapter.itemClickListener = object : ThemeListRVAdapter.ItemClickListener {
            override fun onClick(item: Theme) {
                if(item != null){
                    val intent = Intent(requireActivity(), ThemeDetailActivity::class.java).putExtra("theme", item)
                    startActivity(intent)
                }
            }
        }
    }

    inner class SwitchListener: CompoundButton.OnCheckedChangeListener{
        override fun onCheckedChanged(button: CompoundButton, isChecked: Boolean) {
            if(isChecked){
                // TODO
            } else {
                // TODO
            }
        }

    }


}