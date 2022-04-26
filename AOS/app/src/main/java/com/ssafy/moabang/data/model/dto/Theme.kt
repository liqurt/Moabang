package com.ssafy.moabang.data.model.dto

data class Theme(
    val themeId: Int,
    val cafeId: Int,
    val name: String,
    val img: String,
    val description: String,
    val price: Int,
    val difficulty: Int,
    val player: String,
    val time: Int,
    var genre: String,
    val type: String
)