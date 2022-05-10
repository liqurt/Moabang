package com.ssafy.moabang.data.model.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.moabang.data.model.dto.Review
import com.ssafy.moabang.data.model.dto.ReviewForCreate
import com.ssafy.moabang.data.model.dto.ReviewForUpdate
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.data.model.repository.Repository
import com.ssafy.moabang.data.model.repository.ReviewRepository
import com.ssafy.moabang.data.model.repository.ThemeRepository
import com.ssafy.moabang.data.model.response.ReviewResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ReviewViewModel: ViewModel() {
    private val reviewRepository = ReviewRepository()

    private val _reviewListLiveData = MutableLiveData<List<ReviewResponse>>()
    val reviewListLiveData : LiveData<List<ReviewResponse>>
        get() = _reviewListLiveData

    private val totalReviewList = mutableListOf<ReviewResponse>()

    fun reviewAdd(review: ReviewForCreate) = viewModelScope.launch {
        addReview(review)
    }

    fun getReview(tid: Int) = viewModelScope.launch {
        readReview(tid)
    }

    fun reviewUpdate(rid: Int, review: ReviewForUpdate) = viewModelScope.launch {
        updateReview(rid, review)
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
                Log.d("THEME VIEWMODEL TEST", "addReview FAILED: ${result.body()}")
            }
        }
        _reviewListLiveData.postValue(totalReviewList)
    }

    private suspend fun readReview(tid: Int) = withContext(Dispatchers.IO) {
        val result: Response<List<ReviewResponse>>? = reviewRepository.readReview(tid)

        if(result != null){
            if(result.isSuccessful){
                totalReviewList.clear()
                result.body()!!.forEach {
                    if(!totalReviewList.contains(it)){
                        totalReviewList.add(it)
                    }
                }
            }
        }
        _reviewListLiveData.postValue(totalReviewList)
    }

    private suspend fun updateReview(rid: Int, review: ReviewForUpdate) = withContext(Dispatchers.IO) {
        val result: Response<String>? = reviewRepository.updateReview(rid, review)

        if(result != null){
            if(result.isSuccessful){
                if(result.body()!! == "true"){
                    Log.d("REVIEW VIEWMODEL TEST", "updateReview SUCCESS: ${result.body()}")
                } else {
                    Log.d("REVIEW VIEWMODEL TEST", "updateReview FAILED - result is false: ${result.body()}")
                }
            } else {
                Log.d("THEME VIEWMODEL TEST", "updateReview FAILED - result is null: ${result.body()}")
            }
        }
        _reviewListLiveData.postValue(totalReviewList)
    }
}