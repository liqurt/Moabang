package com.ssafy.moabang.data.model.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.moabang.data.model.dto.Review
import com.ssafy.moabang.data.model.dto.ReviewForCreate
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.data.model.repository.Repository
import com.ssafy.moabang.data.model.repository.ReviewRepository
import com.ssafy.moabang.data.model.repository.ThemeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ReviewViewModel: ViewModel() {
    private val reviewRepository = ReviewRepository()

    private val _reviewListLiveData = MutableLiveData<List<Review>>()
    val reviewListLiveData : LiveData<List<Review>>
        get() = _reviewListLiveData

    private val totalReviewList = mutableListOf<Review>()

    fun reviewAdd(review: ReviewForCreate) = viewModelScope.launch {
        addReview(review)
    }

    private suspend fun addReview(review: ReviewForCreate) = withContext(Dispatchers.IO) {
        val result: Response<String>? = reviewRepository.addReview(review)

        if(result != null){
            if(result.isSuccessful){
                if(result.body()!! == "true"){
                    Log.d("REVIEW VIEWMODEL TEST", "addReview SUCCESS: ${result.body()}")
                } else {
                    Log.d("REVIEW VIEWMODEL TEST", "addReview FAILED: ${result.body()}")
                }
            } else {
                Log.d("THEME VIEWMODEL TEST", "changeLike FAILED: ${result.body()}")
            }
        }
    }
}