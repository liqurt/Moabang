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

    @Query("DELETE FROM Theme")
    fun clearAll()

}