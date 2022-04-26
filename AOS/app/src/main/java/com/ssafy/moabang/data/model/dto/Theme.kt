package com.ssafy.moabang.data.model.dto

data class Theme(
    val themeId: Int,
    val cafeName: String,
    val name: String,
    val img: String,
    val description: String,
    val price: Int,
    val difficulty: Int,
    val player: String,
    val time: Int,
    val genre: String,
    val rating: Double,
    var like: Boolean,
    val type: String
)