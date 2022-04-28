package com.ssafy.moabang.data.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cafe(
    @PrimaryKey var cid : Int,
    var oid : Int,
    var sido : String,
    var gungu : String,
    var name : String,
    var url : String,
    var time : String,
    var img : String,
    var latitude : Double,
    var longitude : Double,
    var location : String, // 도로명주소
    var tel : String
)
