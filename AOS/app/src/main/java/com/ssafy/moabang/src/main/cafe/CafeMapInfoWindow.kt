package com.ssafy.moabang.src.main.cafe

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.ssafy.moabang.R
import com.ssafy.moabang.data.model.dto.Cafe
import com.ssafy.moabang.data.model.repository.Repository
import kotlinx.coroutines.*

class CafeMapInfoWindow(context: Context) : GoogleMap.InfoWindowAdapter,
    GoogleMap.OnInfoWindowClickListener {

    var mContext = context
    var mWindow = (context as Activity).layoutInflater.inflate(R.layout.info_window_custom, null)

    private fun renderWindowText(marker: Marker, view: View) {
        val tvTitle = view.findViewById<TextView>(R.id.info_window_cname)
        val ivImg = view.findViewById<ImageView>(R.id.info_window_img)

        tvTitle.text = marker.title
        Glide.with(mContext)
            .load(marker.snippet)
            .placeholder(R.drawable.door)
            .centerCrop()
            .into(ivImg)
    }


    override fun getInfoContents(marker: Marker): View? {
        return null
    }

    override fun getInfoWindow(marker: Marker): View? {
        renderWindowText(marker, mWindow)
        return mWindow
    }

    override fun onInfoWindowClick(p0: Marker) {
        val intent = Intent(mContext, CafeDetailActivity::class.java).putExtra("cname", p0.title)
        startActivity(mContext, intent, null)
    }

}