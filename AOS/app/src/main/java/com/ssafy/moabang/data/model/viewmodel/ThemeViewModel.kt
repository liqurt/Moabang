package com.ssafy.moabang.data.model.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.data.model.repository.Repository
import com.ssafy.moabang.data.model.repository.ThemeRepository
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

    private suspend fun getTheme(jwtToken: String) = withContext(Dispatchers.IO) {
        val result: Response<List<Theme>>? = themeRepository.getAllTheme(jwtToken)

        if(result != null) {
            if (result.isSuccessful) {
                Repository.get().insertThemes(result.body()!!)
                result.body()!!.forEach {
                    if (!totalThemeList.contains(it)) {
                        totalThemeList.add(it)
                    }
                }
            }
            _themeListLiveData.postValue(totalThemeList)
        } else {
            Log.d("THEME VIEWMODEL TEST", "getTheme: response is null")
        }
    }
}