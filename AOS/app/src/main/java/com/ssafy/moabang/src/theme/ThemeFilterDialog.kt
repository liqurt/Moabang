package com.ssafy.moabang.src.theme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.google.android.material.chip.Chip
import com.ssafy.moabang.R
import com.ssafy.moabang.databinding.DialogThemeFilterBinding




class ThemeFilterDialog : DialogFragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: DialogThemeFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogThemeFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = resources.getStringArray(R.array.cafe_list_island)


        binding.spThemeD.apply{
            adapter = ArrayAdapter<String>(requireContext(), R.layout.spinner_text, list)
            onItemSelectedListener = this@ThemeFilterDialog
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
            binding.cgThemeD.removeAllViews()
            for (item in strings) {
                binding.cgThemeD.addView(Chip(requireContext()).apply {
                    text = item
                    isCheckable = true
                    isCheckedIconVisible = true
                    setCheckedIconTintResource(R.color.moabang_pink)
                    setChipBackgroundColorResource(R.color.white)
                    setChipStrokeColorResource(R.color.moabang_pink)
                    setChipStrokeWidthResource(R.dimen.chipStrokeSize)
                    setTextAppearanceResource(R.style.ChipTextStyle2)
                    chipMinHeight = 100F
                })
            }
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}