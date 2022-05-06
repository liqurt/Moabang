/**
* 1. 지도 띄우기[DONE]
* 2. 위치 관련 퍼미션[DONE]
* 3. 내 위치로 카메라 이동
* 4. 카페들의 위도 및 경도로 마커 추가
* 4. 마커 클릭시, 카페 상세로 이동
* 6. 줌 아웃시, 마커 간소화
*/
package com.ssafy.moabang.src.main.cafe

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.ssafy.moabang.R
import com.ssafy.moabang.databinding.FragmentCafeMapBinding

class CafeMapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentCafeMapBinding
    private lateinit var mMap: GoogleMap
    private val permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            Log.d("CafeMapFragment", "권한 승인 받음")
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {
            Toast.makeText(
                requireActivity(),
                "사용자의 위치를 파악할 권한이 없습니다.",
                Toast.LENGTH_SHORT
            ).show()
            Log.d("CafeMapFragment", "권한 승인 안 받음")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCafeMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permissionCheck()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun permissionCheck(){
        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setRationaleMessage("사용자의 주변을 볼 수 있는 지도를 보여주기 위해, 권한이 필요합니다.")
            .setDeniedMessage("\'설정 > 권한\' 에서 다시 권한을 설정 할 수 있습니다.")
            .setGotoSettingButton(true)
            .setGotoSettingButtonText("설정으로 가기")
            .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
            .check()
    }
}