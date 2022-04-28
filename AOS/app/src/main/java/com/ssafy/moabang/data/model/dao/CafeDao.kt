package com.ssafy.moabang.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ssafy.moabang.data.model.dto.Cafe

@Dao
interface CafeDao {

    @Query("SELECT * FROM Cafe")
    fun getAllCafe() : List<Cafe>

    @Query("SELECT * FROM Cafe WHERE sido LIKE :sido AND gungu LIKE :gungu")
    fun getCafeBySidoGungu(sido : String, gungu : String) : List<Cafe>

    @Query("SELECT * FROM Cafe WHERE name LIKE :name")
    fun getCafeByName(name : String) : List<Cafe>

    @Insert
    fun insertCafe(cafe : Cafe)

    @Query("DELETE FROM Cafe")
    fun clearAll()

}