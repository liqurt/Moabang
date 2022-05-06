package com.ssafy.moabang.data.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Theme(
    val time: String,
    val type: String,
    val description: String?,
    val tid: Int,
    val difficulty: Int,
    val tname: String,
    val rplayer: String,
    val genre: String,
    val grade: Double,
    val activity: String,
    val cid: Int,
    var cname: String,
    val img: String,
    var curl: String
): Parcelable