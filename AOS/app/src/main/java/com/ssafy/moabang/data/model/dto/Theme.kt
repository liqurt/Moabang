package com.ssafy.moabang.data.model.dto

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Theme(
    val time: String,
    val type: String,
    val description: String?,
    @PrimaryKey val tid: Int,
    val difficulty: Int,
    val tname: String,
    val rplayer: String,
    val genre: String,
    val grade: Double,
    val activity: String,
    val cid: Int,
    var cname: String,
    val img: String,
    val island: String, // var -> val
    val si: String, // var -> val
    var url: String,
    var islike: Boolean,
    val min_player: Int,
    val max_player: Int
): Parcelable