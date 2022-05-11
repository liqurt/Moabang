package com.ssafy.moabang.src.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.moabang.data.model.dto.ThemeForCompare

object CompareList {
    var items : MutableList<ThemeForCompare> = mutableListOf()
    var clist = mutableListOf<ThemeForCompare>()

    private val _clistLiveData = MutableLiveData<List<ThemeForCompare>>()
    val clistLiveData : LiveData<List<ThemeForCompare>>
        get() = _clistLiveData

    fun addTheme(theme : ThemeForCompare){
        if(!clist.contains(theme)) clist.add(theme)
        _clistLiveData.postValue(clist)
    }
}