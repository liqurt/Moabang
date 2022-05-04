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
import com.ssafy.moabang.src.main.ThemeFragment


class ThemeFilterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: DialogThemeFilterBinding
    private lateinit var genreList : Array<String>
    private lateinit var typeList : Array<String>
    private lateinit var playerList : Array<String>
    private lateinit var diffList : Array<String>
    private lateinit var activeList : Array<String>
    private val tf = ThemeFilter()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = DialogThemeFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        genreList = resources.getStringArray(R.array.genre_list)
        typeList = resources.getStringArray(R.array.type_list)
        playerList = resources.getStringArray(R.array.player_list)
        diffList = resources.getStringArray(R.array.diff_list)
        activeList = resources.getStringArray(R.array.active_list)

        binding.toolbarThemeFilter.ivToolbarIcon.setOnClickListener { this.onBackPressed() }
        binding.toolbarThemeFilter.tvToolbarTitle.text = "테마 필터"
        val list = resources.getStringArray(R.array.cafe_list_island)

        binding.spThemeD.apply{
            adapter = ArrayAdapter<String>(this@ThemeFilterActivity, R.layout.spinner_text, list)
            onItemSelectedListener = this@ThemeFilterActivity
        }

        setChipGroup(binding.cgThemeDGenre, genreList)
        setChipGroup(binding.cgThemeDType, typeList)
        setChipGroup(binding.cgThemeDPlayer, playerList)
        setChipGroup(binding.cgThemeDDiff, diffList)
        setChipGroup(binding.cgThemeDActive, activeList)

        initOnClickListener()

    }

    private fun initOnClickListener(){
        binding.tvThemeDOk.setOnClickListener {
            // 초기화
            tf.si.clear()
            tf.genre.clear()
            tf.type.clear()
            tf.player.clear()
            tf.diff.clear()
            tf.active.clear()
            /////////


            var siList = emptyArray<String>()
            when (tf.island) {
                "서울" -> {
                    siList = resources.getStringArray(R.array.cafe_list_si_seoul)
                }
                "경기/인천" -> {
                    siList = resources.getStringArray(R.array.cafe_list_si_gyeonggi_incheon)
                }
                "충청" -> {
                    siList = resources.getStringArray(R.array.cafe_list_si_chungcheong)
                }
                "강원" -> {
                    siList = resources.getStringArray(R.array.cafe_list_si_gangwon)
                }
                "경상" -> {
                    siList = resources.getStringArray(R.array.cafe_list_si_gyeongsang)
                }
                "전라" -> {
                    siList = resources.getStringArray(R.array.cafe_list_si_jeolla)
                }
                "제주" -> {
                    siList = resources.getStringArray(R.array.cafe_list_si_jeju)
                }
                else -> {
                    siList = emptyArray<String>()
                }
            }
            for(idx in binding.cgThemeDArea.checkedChipIds){
                tf.si.add(siList[idx-1])
            }

            for(idx in binding.cgThemeDGenre.checkedChipIds){
                tf.genre.add(genreList[idx-1])
            }

            for(idx in binding.cgThemeDType.checkedChipIds){
                tf.type.add(typeList[idx-1])
            }

            for(idx in binding.cgThemeDPlayer.checkedChipIds){
                tf.player.add(playerList[idx-1])
            }

            for(idx in binding.cgThemeDDiff.checkedChipIds){
                tf.diff.add(diffList[idx-1])
            }

            for(idx in binding.cgThemeDActive.checkedChipIds){
                tf.active.add(activeList[idx-1])
            }

            ThemeFragment().filter()
            finish()
        }

        binding.tvThemeDCancel.setOnClickListener {
            finish()
        }
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
            when (p0.getItemAtPosition(p2)) {
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
            }
            tf.island = p0.getItemAtPosition(p2).toString()
            binding.cgThemeDArea.removeAllViews()
            setChipGroup(binding.cgThemeDArea, strings)
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}