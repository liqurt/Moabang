package com.ssafy.moabang.data.model.dto



data class UserInfo (
    var loginType: String,
    val userId: String,
    val email: String,
    val name: String,
    val imageUrl: String,
    val prefArea: ArrayList<String>,
    val prefGenre: ArrayList<String>,
    val prefDifficulty: ArrayList<String>
)