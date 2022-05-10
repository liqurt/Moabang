/**
1. UI 짜기(아~주 기초적인 것만) [DONE]
2. 내 근처 카페
- 권한 쳌[DONE]
- 카페 데이터 초기화 - 로컬 DB [DONE] - 이미 LoginActivity에서 초기화함
- 내 위치 파악[DONE]
- 모든 카페 위치 파악[DONE]
- 나랑 가까운 카페 파악[DONE]
- {threshold} 값 이하인 카페 추출[DONE]
- UI 상세 1 - recyclerView
- 추출한 카페를 recyclerView의 adapter에 달아 놓는다.
- UI 상세 2 - auto carousel
 */
package com.ssafy.moabang.src.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.ssafy.moabang.data.model.dto.Cafe
import com.ssafy.moabang.data.model.repository.Repository
import com.ssafy.moabang.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var currentLocation: LatLng? = null

    private var nearCafeList = mutableListOf<Cafe>()
    private var repository = Repository.get()

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

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(): LatLng? {
        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        return if (lastKnownLocation != null) {
            LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
        } else {
            null
        }
    }

    private fun checkPermission() {
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                val seoul = LatLng(37.566535, 126.9779692)
                currentLocation = getCurrentLocation() ?: LatLng(seoul.latitude, seoul.longitude)
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
        val threshold = 10.0 // 10km
        if (currentLocation != null) {
            CoroutineScope(Dispatchers.IO).launch {
                var allCafeList = listOf<Cafe>()
                CoroutineScope(Dispatchers.IO).async {
                    allCafeList = repository.getAllCafe()
                }.await()
                for(cafe in allCafeList) {
                    if(cafe.lat == null || cafe.lat == "" || cafe.lon == null || cafe.lon == "") {
                        continue
                    }else{
                        val cafeLat = cafe.lat!!.toDouble()
                        val cafeLng = cafe.lon!!.toDouble()
                        val distance = getDistanceLatLngInKm(currentLocation!!.latitude, currentLocation!!.longitude, cafeLat, cafeLng)
                        if(distance <= threshold) {
                            nearCafeList.add(cafe)
                            Log.d("AAAAA", "HOME FRAGMENT_nearCafeList : ${cafe.cname}")
                        }
                    }
                }
                Log.d("AAAAA", "HOME FRAGMENT_nearCafeList : ${nearCafeList.size}")
            }
        } else {
            Log.d("AAAAA", "HOME FRAGMENT : currentLocation is null")
        }
    }

    private fun getDistanceLatLngInKm(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Double {
        val R = 6371 // radius of earth in km
        val dLat = deg2rad(lat2 - lat1)
        val dLng = deg2rad(lon2 - lon1)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLng / 2) * Math.sin(
            dLng / 2
        )
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return R * c
    }

    private fun deg2rad(deg: Double): Double {
        return deg * (Math.PI / 180)
    }

}