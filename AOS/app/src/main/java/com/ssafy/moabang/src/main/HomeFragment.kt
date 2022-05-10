/**
1. UI 짜기(아~주 기초적인 것만) [DONE]
2. 내 근처 카페
- 권한 쳌[DONE]
- 카페 데이터 초기화 - 로컬 DB [DONE] - 이미 LoginActivity에서 초기화함
- 내 위치 파악[DONE]
- 모든 카페 위치 파악[IN_PROGRESS]
- 나랑 가까운 카페 파악
- {threshold} 값 이하인 카페 추출
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
import com.ssafy.moabang.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private var currentLocation: LatLng? = null

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
                Log.d("AAAAA", "HOME FRAGMENT_권한 설정됨")
                val seoul = LatLng(37.566535, 126.9779692)
                currentLocation = getCurrentLocation() ?: LatLng(seoul.latitude, seoul.longitude)
                Log.d("AAAAA", "HOME FRAGMENT_currentLocation : $currentLocation")
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

}