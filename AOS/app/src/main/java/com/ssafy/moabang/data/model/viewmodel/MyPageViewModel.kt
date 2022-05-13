package com.ssafy.moabang.data.model.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.data.model.repository.MyPageRepository
import com.ssafy.moabang.data.model.repository.Repository
import com.ssafy.moabang.data.model.response.DoneThemeResponse
import com.ssafy.moabang.data.model.response.DoneTidResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MyPageViewModel: ViewModel() {
    private val mypageRepository = MyPageRepository()

    private val _likeListLiveData = MutableLiveData<List<Theme>>()
    val likeListLiveData : LiveData<List<Theme>>
        get() = _likeListLiveData

    private val _doneThemeListLiveData = MutableLiveData<List<DoneThemeResponse>>()
    val doneThemeListLiveData : LiveData<List<DoneThemeResponse>>
        get() = _doneThemeListLiveData

    private val _doneTidListLiveData = MutableLiveData<List<DoneTidResponse>>()
    val doneTidListLiveData : LiveData<List<DoneTidResponse>>
        get() = _doneTidListLiveData

    private val totalLikeList = mutableListOf<Theme>()
    private val totalDoneThemeList = mutableListOf<DoneThemeResponse>()
    private val totalDoneTidList = mutableListOf<DoneTidResponse>()

    fun getAllLikeTheme() = viewModelScope.launch {
        getLikeTheme()
        Log.d("VIEWMODEL TEST", "getAllLikeTheme: $totalLikeList")
    }

    fun getAllDoneTheme() = viewModelScope.launch {
        getDoneTheme()
    }

    fun getAllDoneTid() = viewModelScope.launch {
        getDoneTid()
    }

    private suspend fun getLikeTheme() = withContext(Dispatchers.IO) {
        val result: Response<List<Theme>>? = mypageRepository.getAllLikeTheme()

        if(result != null) {
            if (result.isSuccessful) {
                Log.d("MYPAGE VIEWMODEL TEST", "success: ${result.body()}")
                result.body()!!.forEach {
                    if (!totalLikeList.contains(it)) {
                        totalLikeList.add(it)
                    }
                }
            }else{
                Log.d("MYPAGE VIEWMODEL TEST", "SAD: ${result.message()}")
            }
            _likeListLiveData.postValue(totalLikeList)
        } else {
            Log.d("MYPAGE VIEWMODEL TEST", "getTheme: response is null")
        }
    }

    private suspend fun getDoneTheme() = withContext(Dispatchers.IO) {
        val result: Response<List<DoneThemeResponse>>? = mypageRepository.getDoneTheme()

        if(result != null) {
            if (result.isSuccessful) {
                Log.d("MYPAGE VIEWMODEL TEST", "success: ${result.body()}")
                result.body()!!.forEach {
                    if (!totalDoneThemeList.contains(it)) {
                        totalDoneThemeList.add(it)
                    }
                }
            }else{
                Log.d("MYPAGE VIEWMODEL TEST", "SAD: ${result.message()}")
            }
            _doneThemeListLiveData.postValue(totalDoneThemeList)
        } else {
            Log.d("MYPAGE VIEWMODEL TEST", "getTheme: response is null")
        }
    }

    private suspend fun getDoneTid() = withContext(Dispatchers.IO) {
        val result: Response<List<DoneTidResponse>>? = mypageRepository.getDoneTid()

        if(result != null) {
            if (result.isSuccessful) {
                Log.d("MYPAGE VIEWMODEL TEST", "success: ${result.body()}")
                result.body()!!.forEach {
                    if (!totalDoneTidList.contains(it)) {
                        totalDoneTidList.add(it)
                    }
                }
            }else{
                Log.d("MYPAGE VIEWMODEL TEST", "SAD: ${result.message()}")
            }
            _doneTidListLiveData.postValue(totalDoneTidList)
        } else {
            Log.d("MYPAGE VIEWMODEL TEST", "getTheme: response is null")
        }
    }
}