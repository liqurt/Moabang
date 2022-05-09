package com.ssafy.moabang.data.model.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.data.model.repository.Repository
import com.ssafy.moabang.data.model.repository.ThemeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ThemeViewModel: ViewModel() {
    private val themeRepository = ThemeRepository()

    private val _themeListLiveData = MutableLiveData<List<Theme>>()
    val themeListLiveData : LiveData<List<Theme>>
        get() = _themeListLiveData

    private val totalThemeList = mutableListOf<Theme>()

    fun getAllTheme(jwtToken: String) = viewModelScope.launch {
        getTheme(jwtToken)
//        Log.d("VIEWMODEL TEST", "getAllTheme: $totalThemeList")
    }

    fun themeLike(tid: Int) = viewModelScope.launch {
        changeLike(tid)
    }

    private suspend fun getTheme(jwtToken: String) = withContext(Dispatchers.IO) {
        val result: Response<List<Theme>>? = themeRepository.getAllTheme()

        if(result != null) {
            if (result.isSuccessful) {
                CoroutineScope(Dispatchers.IO).launch {
                    Repository.get().insertThemes(result.body()!!)
                }
                Log.d("THEME VIEWMODEL TEST", "success: ${result.body()}")
                result.body()!!.forEach {
                    if (!totalThemeList.contains(it)) {
                        totalThemeList.add(it)
                    }
                }
            }else{
                Log.d("THEME VIEWMODEL TEST", "fail: ${result.body()}")
            }
            _themeListLiveData.postValue(totalThemeList)
        } else {
            Log.d("THEME VIEWMODEL TEST", "getTheme: response is null")
        }
    }

    private suspend fun changeLike(tid: Int) = withContext(Dispatchers.IO) {
        val result: Response<String>? = themeRepository.themeLike(tid)

        if(result != null){
            if(result.isSuccessful){
                Log.d("THEME VIEWMODEL TEST", "changeLike SUCCESS: ${result.body()}")
                if(result.body()!!.contains("취소")){
                    Repository.get().setThemeLike(tid, false)
                } else {
                    Repository.get().setThemeLike(tid, true)
                }
            } else {
                Log.d("THEME VIEWMODEL TEST", "changeLike FAILED: ${result.body()}")
            }
        }
    }
}