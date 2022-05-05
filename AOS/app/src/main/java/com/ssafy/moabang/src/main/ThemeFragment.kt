package com.ssafy.moabang.src.main

import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.moabang.adapter.ThemeListRVAdapter
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.databinding.FragmentThemeBinding
import com.ssafy.moabang.src.theme.ThemeDetailActivity
import com.ssafy.moabang.src.theme.ThemeFilterActivity
import android.text.Editable
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.ssafy.moabang.data.model.repository.Repository
import com.ssafy.moabang.src.theme.ThemeFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ThemeFragment : Fragment() {
    private lateinit var binding: FragmentThemeBinding
    private lateinit var repository: Repository
    lateinit var themeListRVAdapter: ThemeListRVAdapter

    private lateinit var originalList: List<Theme>
    private lateinit var filteredList: List<Theme>
    private var searchList = ArrayList<Theme>()

    private val activityResultLauncher : ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == 1){
            val intent = it.data
            val returnValue = intent!!.getParcelableExtra<ThemeFilter>("tf")
            filter(returnValue!!)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = Repository.get()
    }

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

    fun init(){
        themeListRVAdapter = ThemeListRVAdapter()

        CoroutineScope(Dispatchers.Main).launch {
            originalList = repository.getAllTheme()
            themeListRVAdapter.filterList(originalList)
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
            val intent = Intent(requireActivity(), ThemeFilterActivity()::class.java)
            activityResultLauncher.launch(intent)
        }

        search()
    }

    private fun filter(tf: ThemeFilter){
        Log.d("FILTER TEST", "filter: island = ${tf.island}, si = ${tf.si}")
        CoroutineScope(Dispatchers.Main).launch {
//            filteredList = repository.filterThemes(tf.island, tf.si, tf.genre, tf.type, tf.diff, tf.active)
            filteredList = repository.filterThemesArea(tf.island, tf.si)
            themeListRVAdapter.filterList(filteredList)
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
        val list = if(!::filteredList.isInitialized || filteredList.isEmpty()) originalList else  filteredList
        searchList = ArrayList<Theme>()
        for (item in list) {
            if (item.tname.contains(searchText)) {
                searchList.add(item)
            }
        }
        themeListRVAdapter.filterList(searchList)
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