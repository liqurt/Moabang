package com.ssafy.moabang.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssafy.moabang.data.model.dto.Cafe
import com.ssafy.moabang.data.model.dto.Theme

@Dao
interface ThemeDao {

    @Query("SELECT * FROM Theme")
    fun getAllTheme() : List<Theme>

    @Insert
    fun insertTheme(theme : Theme)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertThemes(themeList : List<Theme>)

    // 인원수떔에 일단 보류
//    fun filterThemes(island: String, si: ArrayList<String>, genre: ArrayList<String>, type: ArrayList<String>, player : ArrayList<String>, diff : ArrayList<String>, active : ArrayList<String>)

    @Query("SELECT * FROM Theme WHERE genre IN (:genre) AND type IN (:type) AND difficulty IN (:diff) AND activity IN (:active)")
    fun filterThemesNoAreaTemp(genre: ArrayList<String>, type: ArrayList<String>, diff : ArrayList<Int>, active : ArrayList<String>) : List<Theme>

    @Query("SELECT * FROM Theme WHERE island = :island AND si IN (:si) AND genre IN (:genre) AND type IN (:type) AND difficulty IN (:diff) AND activity IN (:active)")
    fun filterThemesTemp(island: String, si: ArrayList<String>, genre: ArrayList<String>, type: ArrayList<String>, diff : ArrayList<Int>, active : ArrayList<String>) : List<Theme>

    @Query("SELECT * FROM Theme WHERE island = :island AND si IN (:si)")
    fun filterThemesArea(island: String, si: ArrayList<String>) : List<Theme>


    @Query("DELETE FROM Theme")
    fun clearAll()

}