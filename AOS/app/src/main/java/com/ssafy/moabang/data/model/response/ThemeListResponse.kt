package com.ssafy.moabang.data.model.response

import com.google.gson.annotations.SerializedName
import com.ssafy.moabang.config.BaseResponse
import com.ssafy.moabang.data.model.dto.Theme

class ThemeListResponse: BaseResponse() {
    @SerializedName("data")
    val dataSet: List<Theme>? = null
}