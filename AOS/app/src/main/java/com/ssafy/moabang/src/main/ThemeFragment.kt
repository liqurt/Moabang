package com.ssafy.moabang.src.main

import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.moabang.adapter.ThemeListRVAdapter
import com.ssafy.moabang.data.model.viewmodel.ThemeViewModel
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.databinding.FragmentThemeBinding
import com.ssafy.moabang.src.theme.ThemeDetailActivity
import com.ssafy.moabang.src.theme.ThemeFilterActivity
import android.text.Editable
import com.ssafy.moabang.src.theme.ThemeFilter


class ThemeFragment : Fragment() {
    private lateinit var binding: FragmentThemeBinding
    private lateinit var themeListRVAdapter: ThemeListRVAdapter
    private var tf = ThemeFilter()

    private lateinit var originalList: List<Theme>
    private var filteredList = ArrayList<Theme>()
    private var searchList = ArrayList<Theme>()

    val themeViewModel: ThemeViewModel by viewModels()

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
        val sp = requireActivity().getSharedPreferences("moabang", AppCompatActivity.MODE_PRIVATE)
        val token = sp.getString("moabangToken", "")!!

        themeViewModel.getAllTheme(token)
        themeListRVAdapter = ThemeListRVAdapter()

        themeViewModel.themeListLiveData.observe(requireActivity()){
            originalList = it
            themeListRVAdapter.data = it
            themeListRVAdapter.notifyDataSetChanged()
        }

        binding.rvThemeF.apply{
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = themeListRVAdapter
        }

        themeListRVAdapter.itemClickListener = object : ThemeListRVAdapter.ItemClickListener {
            override fun onClick(item: Theme) {
                if(item != null){
                    val intent = Intent(requireActivity(), ThemeDetailActivity::class.java).putExtra("theme", item)
                    startActivity(intent)
                }
            }
        }

        binding.btnThemeFFilter.setOnClickListener{
            startActivity(Intent(requireActivity(), ThemeFilterActivity()::class.java))
        }

        search()
        filter()
    }

    fun filter(){
        if(!(tf.island == "" || tf.island == "전체")){

        }
    }

    private fun search(){
        // 검색
        binding.etThemeFSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                val searchText: String = binding.etThemeFSearch.text.toString()
                searchFilter(searchText)
            }
        })
    }

    fun searchFilter(searchText: String) {
        val list = if(filteredList.size == 0) originalList else  filteredList
        searchList = ArrayList<Theme>()
        for (item in list) {
            if (item.tname.contains(searchText)) {
                searchList.add(item)
            }
        }
        themeListRVAdapter.data = searchList
        binding.rvThemeF.adapter = themeListRVAdapter
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