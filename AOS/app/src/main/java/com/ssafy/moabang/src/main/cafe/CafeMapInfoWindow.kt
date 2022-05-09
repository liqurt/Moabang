package com.ssafy.moabang.src.main.cafe

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.ssafy.moabang.R

class CafeMapInfoWindow(context: Context) : GoogleMap.InfoWindowAdapter{

    var mContext = context
    var mWindow = (context as Activity).layoutInflater.inflate(R.layout.info_window_custom, null)

    private fun rendowWindowText(marker: Marker, view: View){
        val tvTitle = view.findViewById<TextView>(R.id.info_window_cname)
        val ivImg = view.findViewById<ImageView>(R.id.info_window_img)
        tvTitle.text = marker.title
        Glide.with(mContext)
            .load(marker.snippet)
            .placeholder(R.drawable.door)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .centerCrop()
            .into(ivImg)
    }

    override fun getInfoContents(marker: Marker): View {
        rendowWindowText(marker, mWindow)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        rendowWindowText(marker, mWindow)
        return mWindow
    }

}