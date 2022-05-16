package com.ssafy.moabang.src.main.cafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ssafy.moabang.adapter.ThemeListRVAdapter
import com.ssafy.moabang.config.GlobalApplication
import com.ssafy.moabang.data.api.CafeApi
import com.ssafy.moabang.data.model.dto.Cafe
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.data.model.repository.Repository
import com.ssafy.moabang.databinding.ActivityCafeDetailBinding
import com.ssafy.moabang.src.theme.ThemeDetailActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class CafeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCafeDetailBinding
    private lateinit var themeList: List<Theme>
    private var themeListRVAdapter : ThemeListRVAdapter = ThemeListRVAdapter()
    private lateinit var cafe: Cafe
    private var repository = Repository.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCafeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getParcelableExtra<Cafe>("cafe")?.let {
            cafe = it
            initView()
        } ?: findCafeWithTitle()

    }

    private fun findCafeWithTitle() {
        val cname = intent.getStringExtra("cname")
        CoroutineScope(Dispatchers.Main).launch {
            cafe = repository.getCafeByNameExactly(cname!!)
            initView()
        }
    }

    private fun getJWTtoken(): String {
        val sp = getSharedPreferences("moabang", MODE_PRIVATE)
        return sp.getString("moabangToken", "NO_JWT_TOKEN")!!
    }

    private fun initView() {
        // cafe
        Glide.with(this)
            .load(cafe.img)
            .into(binding.ivCafeDetailFImg)
        binding.tvCafeDetailFCname.text = cafe.cname
        binding.tvCafeDetailFCphone.text = cafe.cphone
        binding.tvCafeDetailFLocation.text = cafe.location
        binding.tvCafeDetailFTime.text = cafe.time

        // themes
        val service: CafeApi = GlobalApplication.retrofit.create(CafeApi::class.java)
        val jwtToken = getJWTtoken()
        service.getThemeByCid(jwtToken, cafe.cid).enqueue(object : Callback<List<Theme>> {
            override fun onResponse(call: Call<List<Theme>>, response: Response<List<Theme>>) {
                if (response.isSuccessful) {
                    val data: List<Theme> = response.body()!!
                    Log.d("AAAAA", "themeList : $data")
                    themeList = data
                    themeListRVAdapter.data = themeList
                    themeListRVAdapter.from = "CafeDetailActivity"
                    binding.rvCafeDetailFThemeByCafe.apply {
                        adapter = themeListRVAdapter
                        layoutManager = LinearLayoutManager(this@CafeDetailActivity, LinearLayoutManager.VERTICAL, false)
                        themeListRVAdapter.itemClickListener = object : ThemeListRVAdapter.ItemClickListener {
                            override fun onClick(item: Theme) {
                                Log.d("AAAAA", "CAFE : $cafe")
                                item.apply {
                                    cname = cafe.cname.toString()
                                    url = cafe.url.toString()
                                    island = cafe.island.toString()
                                    si = cafe.location.toString()
                                }
                                Log.d("AAAAA", "ITEM(THEME) : $item")
                                val intent = Intent(
                                    this@CafeDetailActivity,
                                    ThemeDetailActivity::class.java
                                ).putExtra("theme", item)
                                startActivity(intent)
                            }
                        }
                    }
                    Log.d("AAAAA", "data : $data")
                } else {
                    Log.e("AAAAA", "실패!")
                }
            }

            override fun onFailure(call: Call<List<Theme>>, t: Throwable) {
                Log.d("AAAAA", "네트워크 오류2 : ${t.message}")
                Toast.makeText(this@CafeDetailActivity, "네트워크 오류2 : ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}