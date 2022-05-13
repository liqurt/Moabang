package com.ssafy.moabang.data.model.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

data class DoneThemeResponse(
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
    var island: String,
    var si: String,
    var url: String,
    var islike: Boolean,
    val min_player: Int,
    val max_player: Int,
    val count : Int,
    val playDate: String
)