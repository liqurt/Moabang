package com.ssafy.moabang.data.model.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.data.model.repository.MyPageRepository
import com.ssafy.moabang.data.model.repository.Repository
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

    private val totalLikeList = mutableListOf<Theme>()

    fun getAllLikeTheme() = viewModelScope.launch {
        getLikeTheme()
        Log.d("VIEWMODEL TEST", "getAllLikeTheme: $totalLikeList")
    }

    private suspend fun getLikeTheme() = withContext(Dispatchers.IO) {
        val result: Response<List<Theme>>? = mypageRepository.getAllLikeTheme()

        if(result != null) {
            if (result.isSuccessful) {
                CoroutineScope(Dispatchers.IO).launch {
                    Repository.get().insertThemes(result.body()!!)
                }
                Log.d("THEME VIEWMODEL TEST", "success: ${result.body()}")
                result.body()!!.forEach {
                    if (!totalLikeList.contains(it)) {
                        totalLikeList.add(it)
                    }
                }
            }else{
                Log.d("THEME VIEWMODEL TEST", "SAD: ${result.message()}")
            }
            _likeListLiveData.postValue(totalLikeList)
        } else {
            Log.d("THEME VIEWMODEL TEST", "getTheme: response is null")
        }
    }
}