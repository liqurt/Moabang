package com.ssafy.moabang.data.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cafe(
    @PrimaryKey var cid : Int,
    var cname : String?,
    var cphone : String?,
    var url : String?,
    var time : String?,
    var img : String?,
    var location : String?,
    var lat : String?,
    var lon : String?,
    var island : String?, // 도(섬 도 島)
    var si : String?, // 시
)
