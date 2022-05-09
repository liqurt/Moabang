package com.ssafy.moabang.data.api

import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import com.ssafy.moabang.data.model.dto.Review
import com.ssafy.moabang.data.model.dto.ReviewForCreate
import com.ssafy.moabang.data.model.dto.Theme
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewApi {
    @POST("/theme/review/create")
    fun addReview(
        @Body review: ReviewForCreate
    ) : Call<String>
}