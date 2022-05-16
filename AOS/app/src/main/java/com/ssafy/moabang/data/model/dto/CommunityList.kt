package com.ssafy.moabang.data.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommunityList(
    var rid: Int?,
    var user: User?,
    var title: String?,
    var content: String?,
    var header: String?,
    var createDate: String?,
    var updateDate: String?
):Parcelable
