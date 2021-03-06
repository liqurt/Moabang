package com.ssafy.moabang.src.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuInflater
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.moabang.R
import com.ssafy.moabang.adapter.ThemeListRVAdapter
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.data.model.repository.Repository
import com.ssafy.moabang.data.model.viewmodel.MyPageViewModel
import com.ssafy.moabang.databinding.ActivityMyLikeBinding
import com.ssafy.moabang.src.theme.ThemeDetailActivity
import com.ssafy.moabang.src.theme.ThemeFilter
import com.ssafy.moabang.src.theme.ThemeFilterActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyLikeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyLikeBinding
    private lateinit var repository: Repository
    private lateinit var themeListRVAdapter: ThemeListRVAdapter
    private val mypageViewModel: MyPageViewModel by viewModels()

    private lateinit var originalList: List<Theme>
    private lateinit var filteredList: List<Theme>
    private var searchList = ArrayList<Theme>()
    private lateinit var tf: ThemeFilter

    private val activityResultLauncher : ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == 1){
            val intent = it.data
            val returnValue = intent!!.getParcelableExtra<ThemeFilter>("tf")
            if (returnValue != null) {
                tf = returnValue
            }
            filter(returnValue!!)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyLikeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        binding.toolbarLike.ivToolbarIcon.setOnClickListener { finish() }
        binding.toolbarLike.tvToolbarTitle.text = "?????? ??????"
        repository = Repository.get()

        mypageViewModel.getAllLikeTheme()
        themeListRVAdapter = ThemeListRVAdapter()
        themeListRVAdapter.notifyDataSetChanged()


        mypageViewModel.likeListLiveData.observe(this){
            originalList = it
            themeListRVAdapter.filterList(it)
        }

        binding.rvMyLike.apply{
            layoutManager = LinearLayoutManager(this@MyLikeActivity, LinearLayoutManager.VERTICAL, false)
            adapter = themeListRVAdapter
        }

        themeListRVAdapter.itemClickListener = object : ThemeListRVAdapter.ItemClickListener {
            override fun onClick(item: Theme) {
                if(item != null){
                    val intent = Intent(this@MyLikeActivity, ThemeDetailActivity::class.java).putExtra("theme", item.tid)
                    activityResultLauncher.launch(intent)
                }
            }
        }

        // ??????
        binding.btnMyLikeFilter.setOnClickListener{
            val intent = Intent(this, ThemeFilterActivity()::class.java)
            activityResultLauncher.launch(intent)
        }

        // ??????
        binding.btnMyLikeSort.setOnClickListener {
            showPopup(binding.btnMyLikeSort)
        }

        search()
    }

    private fun showPopup(v: View) {
        val popup = PopupMenu(this, v)
        val inflater: MenuInflater = popup.menuInflater
        var list = if(!::filteredList.isInitialized || filteredList.isEmpty()) originalList else filteredList
        inflater.inflate(R.menu.theme_sort_menu, popup.menu)
        popup.setOnMenuItemClickListener { it ->
            when(it.itemId){
                R.id.sort_by_rate_descending -> {
                    list = list.sortedByDescending { it.grade }
                    themeListRVAdapter.filterList((list))
                    true
                }
                R.id.sort_by_rate_ascending -> {
                    list = list.sortedBy { it.grade }
                    themeListRVAdapter.filterList((list))
                    true
                }
                R.id.sort_by_tname_descending -> {
                    list = list.sortedByDescending { it.tname }
                    themeListRVAdapter.filterList((list))
                    true
                }
                R.id.sort_by_tname_ascending -> {
                    list = list.sortedBy { it.tname }
                    themeListRVAdapter.filterList((list))
                    true
                }
                R.id.sort_by_cname_descending -> {
                    list = list.sortedByDescending { it.cname }
                    themeListRVAdapter.filterList((list))
                    true
                }
                R.id.sort_by_cname_ascending -> {
                    list = list.sortedBy { it.cname }
                    themeListRVAdapter.filterList((list))
                    true
                }
                else -> false
            }
        }
        popup.show()
    }



    private fun filter(tf: ThemeFilter){
        Log.d("FILTER TEST", "filter: island = ${tf.island}, si = ${tf.si}")

        if(tf.island != "??????" && tf.si.size == 0){
            when (tf.island) {
                "??????" -> {
                    resources.getStringArray(R.array.cafe_list_si_seoul).toCollection(tf.si)
                }
                "??????/??????" -> {
                    resources.getStringArray(R.array.cafe_list_si_gyeonggi_incheon).toCollection(tf.si)
                }
                "??????" -> {
                    resources.getStringArray(R.array.cafe_list_si_chungcheong).toCollection(tf.si)
                }
                "??????" -> {
                    resources.getStringArray(R.array.cafe_list_si_gangwon).toCollection(tf.si)
                }
                "??????" -> {
                    resources.getStringArray(R.array.cafe_list_si_gyeongsang).toCollection(tf.si)
                }
                "??????" -> {
                    resources.getStringArray(R.array.cafe_list_si_jeolla).toCollection(tf.si)
                }
                "??????" -> {
                    resources.getStringArray(R.array.cafe_list_si_jeju).toCollection(tf.si)
                }
            }
            Log.d("TOCOLLECTION TEST", "tf.si : ${tf.si}")
        }
        if(tf.genre.size == 0) resources.getStringArray(R.array.genre_list).toCollection(tf.genre)
        if(tf.type.size == 0) resources.getStringArray(R.array.type_list).toCollection(tf.type)
        if(tf.diff.size == 0){
            for(i in 1..5) tf.diff.add(i)
        }
        if(tf.active.size == 0) {
            resources.getStringArray(R.array.active_list).toCollection(tf.active)
            tf.active.add("")
        }

        var minp = 0
        var maxp = 0
        if(tf.player.size > 0){
            minp = tf.player[0]
            if(tf.player.size == 1){
                maxp = tf.player[0]
            } else {
                maxp = tf.player[tf.player.size - 1]
            }
            if(maxp == 5) maxp = 10
        } else {
            minp = 0
            maxp = 10
        }

        var plist = ArrayList<Int>()
        for(i in minp..maxp){
            plist.add(i)
        }

        CoroutineScope(Dispatchers.Main).launch {
            filteredList = if(tf.island == "??????"){
                repository.filterLikeThemesNoArea(tf.genre, tf.type, plist, tf.diff, tf.active)
            } else {
                repository.filterLikeThemes(tf.island, tf.si, tf.genre, tf.type, plist, tf.diff, tf.active)
            }
            themeListRVAdapter.filterList(filteredList)
            if(filteredList.isEmpty()) Toast.makeText(this@MyLikeActivity, "????????? ???????????? ????????? ????????????", Toast.LENGTH_SHORT).show()
        }
    }

    private fun search(){
        // ??????
        binding.etMyLikeSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                val searchText: String = binding.etMyLikeSearch.text.toString()
                searchFilter(searchText)
            }
        })
    }

    fun searchFilter(searchText: String) {
        val list = if(!::filteredList.isInitialized || filteredList.isEmpty()) originalList else filteredList
        searchList = ArrayList<Theme>()
        for (item in list) {
            if (item.tname.lowercase().contains(searchText.lowercase())) {
                searchList.add(item)
            }
        }
        themeListRVAdapter.filterList(searchList)
    }
}