package com.ssafy.moabang.data.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ssafy.moabang.data.model.dao.CafeDao
import com.ssafy.moabang.data.model.dto.Cafe

@Database(entities = [Cafe::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cafeDao() : CafeDao
}