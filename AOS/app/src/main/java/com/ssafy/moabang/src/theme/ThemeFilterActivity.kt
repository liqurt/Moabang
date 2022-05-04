package com.ssafy.moabang.src.theme

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.ssafy.moabang.R
import com.ssafy.moabang.databinding.DialogThemeFilterBinding




class ThemeFilterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: DialogThemeFilterBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = DialogThemeFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        binding.toolbarThemeFilter.ivToolbarIcon.setOnClickListener { this.onBackPressed() }
        binding.toolbarThemeFilter.tvToolbarTitle.text = "테마 필터"
        val list = resources.getStringArray(R.array.cafe_list_island)

        binding.spThemeD.apply{
            adapter = ArrayAdapter<String>(this@ThemeFilterActivity, R.layout.spinner_text, list)
            onItemSelectedListener = this@ThemeFilterActivity
        }

        setChipGroup(binding.cgThemeDGenre, resources.getStringArray(R.array.genre_list))
        setChipGroup(binding.cgThemeDType, resources.getStringArray(R.array.type_list))
        setChipGroup(binding.cgThemeDPlayer, resources.getStringArray(R.array.player_list))
        setChipGroup(binding.cgThemeDDiff, resources.getStringArray(R.array.diff_list))
        setChipGroup(binding.cgThemeDActive, resources.getStringArray(R.array.active_list))

    }

    private fun setChipGroup(res: ChipGroup, list: Array<String>){
        for(item in list){
            res.addView(Chip(this).apply {
                text = item
                isCheckable = true
                isCheckedIconVisible = true
                setCheckedIconTintResource(R.color.moabang_pink)
                setChipBackgroundColorResource(R.color.white)
                setTextAppearanceResource(R.style.ChipTextStyle2)
            })
        }
    }


    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0 == binding.spThemeD) {
            var strings: Array<String> = emptyArray()
            when (p0.getItemAtPosition(p2)) { // island에 맞게, si spinner의 내용물 설정
                "전체" -> {
                    strings = emptyArray()
                }
                "서울" -> {
                    strings = resources.getStringArray(R.array.cafe_list_si_seoul)
                }
                "경기/인천" -> {
                    strings = resources.getStringArray(R.array.cafe_list_si_gyeonggi_incheon)
                }
                "충청" -> {
                    strings = resources.getStringArray(R.array.cafe_list_si_chungcheong)
                }
                "강원" -> {
                    strings = resources.getStringArray(R.array.cafe_list_si_gangwon)
                }
                "경상" -> {
                    strings = resources.getStringArray(R.array.cafe_list_si_gyeongsang)
                }
                "전라" -> {
                    strings = resources.getStringArray(R.array.cafe_list_si_jeolla)
                }
                "제주" -> {
                    strings = resources.getStringArray(R.array.cafe_list_si_jeju)
                }
//            vm.island = p0.getItemAtPosition(p2).toString()
            }
            binding.cgThemeDArea.removeAllViews()
            setChipGroup(binding.cgThemeDArea, strings)
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}