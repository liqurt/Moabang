package com.ssafy.moabang.data.model.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

data class DoneThemeResponse(
    val tid: Int,
    val tname: String,
    val rating: Double,     // from review
    val isSuccess: Int,     // from theme
    val player: Int,        // from review
    var cname: String,      // from theme
    val img: String,        // from theme
    val playDate: String    // from review
)