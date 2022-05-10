package com.ssafy.moabang.src.main.cafe

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.ssafy.moabang.R
import com.ssafy.moabang.data.model.dto.Cafe
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
    private lateinit var infoWindow: View

    private lateinit var clusterManager: ClusterManager<CafeMapClusterItem>

    @SuppressLint("MissingPermission")
    private fun setUpClusterer() {
        if (mMap != null) {
            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.566535, 126.9779692), 10f))
            clusterManager = ClusterManager(context, mMap)
            mMap?.apply {
                setOnCameraIdleListener(clusterManager)
                setOnMarkerClickListener(clusterManager)
                isMyLocationEnabled = true
            }
            addItems()

            clusterManager.markerCollection.setInfoWindowAdapter(object :
                GoogleMap.InfoWindowAdapter {
                override fun getInfoContents(p0: Marker): View? {
                    return null
                }

                override fun getInfoWindow(marker: Marker): View? {
                    val v = (context as Activity).layoutInflater.inflate(
                        R.layout.info_window_custom,
                        null
                    )
                    val tvTitle = v.findViewById<TextView>(R.id.info_window_cname)
                    val ivImg = v.findViewById<ImageView>(R.id.info_window_img)

                    tvTitle.text = marker.title
                    Glide.with(requireContext())
                        .load(marker.snippet)
                        .placeholder(R.drawable.door)
                        .centerCrop()
                        .into(ivImg)
                    return v
                }
            })

            clusterManager.markerCollection.setOnInfoWindowClickListener { marker ->
                val intent = Intent(requireContext(), CafeDetailActivity::class.java).putExtra("cname", marker.title)
                ContextCompat.startActivity(requireContext(), intent, null)
            }
        }
    }

    private fun addItems() {
        CoroutineScope(Dispatchers.Main).launch {
            repository.getAllCafe().forEach {
                if (it.cname == "" || it.lat == "" || it.lon == "" || it.img == "" || it.img == null) {
                    Log.d("AAAAA", "정보가 부족한 카페 : ${it.cname}")
                } else {
                    val marker = CafeMapClusterItem(
                        it.lat!!.toDouble(),
                        it.lon!!.toDouble(),
                        it.cname!!,
                        it.img!!,
                        it.img!!
                    )
                    clusterManager.addItem(marker)
                }
            }
        }
    }

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
        infoWindow = requireActivity().layoutInflater.inflate(R.layout.info_window_custom, null)
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        checkPermission()
        mMap = googleMap
        setUpClusterer()
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
}


