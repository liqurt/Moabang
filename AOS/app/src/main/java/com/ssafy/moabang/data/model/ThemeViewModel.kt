package com.ssafy.moabang.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.data.model.repository.ThemeRepository
import com.ssafy.moabang.data.model.response.ThemeListResponse
import com.ssafy.moabang.util.Resource
import com.ssafy.moabang.util.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ThemeViewModel: ViewModel() {
    val themeRepository = ThemeRepository()
    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val _themeResponseStatus = MutableLiveData<Resource<*>>()
    val themeResponseStatus: LiveData<Resource<*>>
        get() = _themeResponseStatus

    private val _themeListLiveData = MutableLiveData<List<Theme>>()
    val themeListLiveData : LiveData<List<Theme>>
        get() = _themeListLiveData

    private val totalThemeList = mutableListOf<Theme>()

    fun getAllTheme(jwtToken: String) = viewModelScope.launch {
        getTheme(jwtToken)
        Log.d("VIEWMODEL TEST", "getAllTheme: $totalThemeList")
    }

    suspend fun getTheme(jwtToken: String) = withContext(Dispatchers.IO) {
        val result: Resource<ThemeListResponse> = themeRepository.getAllTheme(jwtToken)
        _themeResponseStatus.postValue(result)
        if(result.status == Status.SUCCESS){
            result.data!!.dataSet!!.forEach {
                if(!totalThemeList.contains(it)) {
                    totalThemeList.add(it)
                }
            }
        }
        _themeListLiveData.postValue(totalThemeList)
    }
}