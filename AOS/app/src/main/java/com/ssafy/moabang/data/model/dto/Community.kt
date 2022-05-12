package com.ssafy.moabang.data.model.dto

import java.time.LocalDateTime

data class Community(
    var rid : Int,
    var user : User,
    var title : String,
    var content : String,
    var createDate : LocalDateTime,
    var updateDate : LocalDateTime,
    var header : String,
)
// LocalDateTime