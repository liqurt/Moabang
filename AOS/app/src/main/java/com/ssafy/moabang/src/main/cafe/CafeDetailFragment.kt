package com.ssafy.moabang.src.main.cafe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ssafy.moabang.adapter.ThemeListRVAdapter
import com.ssafy.moabang.config.GlobalApplication
import com.ssafy.moabang.data.api.CafeApi
import com.ssafy.moabang.data.model.dto.Cafe
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.databinding.FragmentCafeDetailBinding
import com.ssafy.moabang.src.theme.ThemeDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CafeDetailFragment(var cafe: Cafe) : Fragment() {

    private lateinit var binding: FragmentCafeDetailBinding
    private lateinit var themeList: List<Theme>
    private var themeListRVAdapter : ThemeListRVAdapter = ThemeListRVAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCafeDetailBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("AAAAA", "cafe from cafeListFragment : $cafe")
        initView()
    }


    private fun getJWTtoken(): String {
        val sp = requireActivity().getSharedPreferences("moabang", AppCompatActivity.MODE_PRIVATE)
        return sp.getString("moabangToken", "NO_JWT_TOKEN")!!
    }

    private fun initView() {
        Log.d("AAAAA","initView()")
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
                    binding.rvCafeDetailFThemeByCafe.apply {
                        adapter = themeListRVAdapter
                        layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                        themeListRVAdapter.itemClickListener = object : ThemeListRVAdapter.ItemClickListener {
                            override fun onClick(item: Theme) {
//                                  GET:/cafe/theme/{cid} 에서 받아오는 테마와 (cname있음!)
//                                  GET:/cafe/theme/list 에서 받아오는 테마가 다르다!(cname없음!)
//                                  해결법 1 : 백엔드에 문의 해서 받아오는 테마 양식을 통일 (요청했고, 대기증)
//                                  해결법 2 : 안드로이드에서 필요한 데이터는 cafe.blahblahblah로 갖고오기(테마쪽 코드랑 충돌 가능성 있음) -> 지금 하고있어요!
                                val intent = Intent(
                                    requireActivity(),
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
                Toast.makeText(requireContext(), "네트워크 오류2 : ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}