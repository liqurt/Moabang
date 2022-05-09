/**
 * 1. 지도 띄우기[DONE]
 * 2. 위치 관련 퍼미션[DONE]
 * 3. 내 위치로 카메라 이동[DONE] - [NEED_REFACTORING] 카메라 이동을 해야함.
 * 4. 카페들의 위도 및 경도로 마커 추가[IN_PROGRESS]
 * 5. 마커 클릭시, 카페 상세로 이동
 * 6. 줌 아웃시, 마커 간소화
 */
package com.ssafy.moabang.src.main.cafe

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.ssafy.moabang.R
import com.ssafy.moabang.data.model.repository.Repository
import com.ssafy.moabang.databinding.FragmentCafeMapBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class CafeMapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentCafeMapBinding
    private var mMap: GoogleMap? = null
    private var currentLocation: LatLng? = null
    private lateinit var repository: Repository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        repository = Repository.get()
        binding = FragmentCafeMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        checkPermission()
        mMap = googleMap
        mMap?.apply {
            isMyLocationEnabled = true
        }
        addCafeMarker()
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
                Log.d("AAAAA", "권한 설정됨")
                val seoul = LatLng(37.566535, 126.9779692)
                currentLocation = getCurrentLocation() ?: LatLng(seoul.latitude, seoul.longitude)
                mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation!!, 16.0f))
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

    private fun addCafeMarker() {
        CoroutineScope(Dispatchers.Main).launch {
            repository.getAllCafe().forEach {
                if (it.cname == "" || it.lat == "" || it.lon == "") {
                    Log.d("AAAAA", "정보가 부족한 카페 : ${it.cname}")
                } else {
                    mMap?.addMarker(
                        MarkerOptions().position(
                            LatLng(it.lat!!.toDouble(), it.lon!!.toDouble())
                        ).title(it.cname)
                    )
                }
            }
        }
    }
}

// 비밀의 화원 혜화점 37.5828365, 127.0021265
// 비트포비아 미션브레이크