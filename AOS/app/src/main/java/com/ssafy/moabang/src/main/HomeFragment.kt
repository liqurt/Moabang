/**
1. **[/cafe/theme/list](http://k6d205.p.ssafy.io:8080/swagger-ui.html#/operations/%EC%B9%B4%ED%8E%98%20%EB%B0%8F%20%ED%85%8C%EB%A7%88%20Api/findAllThemeUsingGET) 로 GET 요청**
2. Count 별로 줄 세우기
3. 10개(?) 정도 골라서 어댑터에 담기
4. 리사이클러뷰 설정
5. 오토 캐러셀(요건 옵션)
 */
package com.ssafy.moabang.src.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.gms.maps.model.LatLng
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.ssafy.moabang.adapter.Latest5CommunityRVAdapter
import com.ssafy.moabang.adapter.NearCafeListRVAdapter
import com.ssafy.moabang.adapter.ThemeListRVAdapter
import com.ssafy.moabang.data.model.dto.Cafe
import com.ssafy.moabang.data.model.dto.Community
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.data.model.repository.CafeRepository
import com.ssafy.moabang.data.model.repository.CommunityRepository
import com.ssafy.moabang.data.model.repository.Repository
import com.ssafy.moabang.databinding.FragmentHomeBinding
import com.ssafy.moabang.src.main.cafe.CafeDetailActivity
import com.ssafy.moabang.src.util.LocationUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import com.ssafy.moabang.src.theme.ThemeDetailActivity
import kotlinx.coroutines.*
import retrofit2.Response


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var currentLocation: LatLng? = null

    private var repository = Repository.get()

    private var nearCafeListRVAdapter: NearCafeListRVAdapter = NearCafeListRVAdapter(listOf())
    private lateinit var nearCafeList: List<Cafe>

    private var hotThemeListRVAdapter: ThemeListRVAdapter = ThemeListRVAdapter()
    private var hotThemeList: List<Theme> = listOf()

    private val snapHelperForNearCafe = LinearSnapHelper()
    private val snapHelperForHotTheme = LinearSnapHelper()

    private var cafeRepository = CafeRepository()

    private lateinit var latest5CommunityList : List<Community>
    private lateinit var latest5CommunityRVAdapter: Latest5CommunityRVAdapter

    private var recruitRepository = CommunityRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHotThemeList()
        setLatest5RecruitList()
    }

    private fun checkPermission() {
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                val seoul = LatLng(37.566535, 126.9779692)
                currentLocation = LocationUtil().getCurrentLocation(requireContext()) ?: LatLng(seoul.latitude, seoul.longitude)
                Log.d("AAAAA", "HOME FRAGMENT_currentLocation : $currentLocation")
                setNearCafeList()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(requireContext(), "위치 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setRationaleMessage("주위의 방탈출 카페를 보기위해 권한이 필요합니다.")
            .setDeniedMessage("[설정] 에서 위치 접근 권한을 부여해야만 사용이 가능합니다.")
            .setPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .setGotoSettingButton(true)
            .setGotoSettingButtonText("[설정] 에서 위치 접근 권한을 허용 할 수 있습니다.")
            .check()
    }

    private fun setNearCafeList() {
        //test
        // 제주 동쪽 33.455988, 126.894981
        if (currentLocation != null) {
            CoroutineScope(Dispatchers.Main).launch {
                var allCafeList = listOf<Cafe>()
                var tempNearCafeList = mutableListOf<Cafe>()
                CoroutineScope(Dispatchers.IO).async {
                    allCafeList = repository.getAllCafe()
                }.await()
                for (cafe in allCafeList) {
                    if (cafe.lat == null || cafe.lat == "" || cafe.lon == null || cafe.lon == "") {
                        continue
                    } else {
                        val cafeLat = cafe.lat!!.toDouble()
                        val cafeLng = cafe.lon!!.toDouble()
                        val distance = LocationUtil().getDistanceLatLngInKm(
                            currentLocation!!.latitude,
                            currentLocation!!.longitude,
                            cafeLat,
                            cafeLng
                        )

                        cafe.distance = distance
                        tempNearCafeList.add(cafe)
                    }
                }
                tempNearCafeList.sortBy { it.distance }
                tempNearCafeList = tempNearCafeList.subList(0, 6)
                nearCafeList = tempNearCafeList
                initNearCafeRCV()
            }
        } else {
            Log.d("AAAAA", "HOME FRAGMENT : currentLocation is null")
        }
    }

    private fun setHotThemeList() {
        var allThemeListWithLike = listOf<Theme>()
        var tempThemeListWithLike = mutableListOf<Theme>()
        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
                allThemeListWithLike = getAllThemeWithLike()
            }.await()
            for (theme in allThemeListWithLike) {
                if (theme.count == 0) {
                    continue
                } else {
                    tempThemeListWithLike.add(theme)
                }
            }
            tempThemeListWithLike.sortByDescending { it.count }
            if (tempThemeListWithLike.size > 6) {
                tempThemeListWithLike = tempThemeListWithLike.subList(0, 6)
            }
            hotThemeList = tempThemeListWithLike
            Log.d("AAAAA", "HOME FRAGMENT_hotThemeList : $hotThemeList")
            initHotThemeRCV()
        }
    }

    private fun setLatest5RecruitList() {
        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
                latest5CommunityList = getLatest5Community()
            }.await()
            initLatest5CommunityRCV()
        }
    }

    private fun initNearCafeRCV() {
        if (nearCafeList.isNotEmpty()) {
            binding.tvHomeFHotThemeEmpty.visibility = View.GONE

            nearCafeListRVAdapter = NearCafeListRVAdapter(nearCafeList)
            nearCafeListRVAdapter.setItemClickListener(object :
                NearCafeListRVAdapter.CafeItemClickListener {
                override fun onClick(cafe: Cafe) {
                    val intent =
                        Intent(requireActivity(), CafeDetailActivity::class.java).putExtra(
                            "cafe",
                            cafe
                        )
                    startActivity(intent)
                }
            })
            binding.rvHomeFNearCafe.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = nearCafeListRVAdapter
                visibility = View.VISIBLE
            }
            snapHelperForNearCafe.attachToRecyclerView(binding.rvHomeFNearCafe)
        } else {
            binding.rvHomeFNearCafe.visibility = View.GONE
            binding.tvHomeFHotThemeEmpty.visibility = View.VISIBLE
        }
    }

    private fun initHotThemeRCV() {
        hotThemeListRVAdapter.apply {
            from = "HomeFragment"
            data = hotThemeList
            itemClickListener = object :
                ThemeListRVAdapter.ItemClickListener {
                override fun onClick(item: Theme) {
                    val intent =
                        Intent(requireActivity(), ThemeDetailActivity::class.java).putExtra(
                            "theme",
                            item
                        )
                    startActivity(intent)
                }
            }
        }
        binding.rvHomeFHotTheme.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = hotThemeListRVAdapter
        }
        snapHelperForHotTheme.attachToRecyclerView(binding.rvHomeFHotTheme)
    }


    private fun initLatest5CommunityRCV() {
        if(latest5CommunityList.isNotEmpty()){
            latest5CommunityRVAdapter = Latest5CommunityRVAdapter(latest5CommunityList)
            binding.rvHomeFLatest5Community.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = latest5CommunityRVAdapter
            }
        }
    }

    private suspend fun getAllThemeWithLike(): List<Theme> = withContext(Dispatchers.IO) {
        val result: Response<List<Theme>>? = cafeRepository.getAllThemeWithLike()
        if (result != null) {
            Log.d("AAAAA", "HOME FRAGMENT_getAllThemeWithLike : ${result.body()}")
            return@withContext result.body()!!
        } else {
            Log.d("AAAAA", "HOME FRAGMENT_getAllThemeWithLike : null")
            return@withContext emptyList()
        }
    }

    private suspend fun getLatest5Community() : List<Community> = withContext(Dispatchers.IO) {
        val result: Response<List<Community>>? = recruitRepository.getLatest5Community()
        if (result != null) {
            Log.d("AAAAA", "HOME FRAGMENT_getLatest5Community : ${result.body()}")
            return@withContext result.body()!!
        } else {
            Log.d("AAAAA", "HOME FRAGMENT_getLatest5Community : null")
            return@withContext emptyList()
        }
    }
}