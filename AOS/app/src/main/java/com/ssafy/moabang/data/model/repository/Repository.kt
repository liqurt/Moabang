package com.ssafy.moabang.data.model.repository

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.withTransaction
import com.ssafy.moabang.config.GlobalApplication
import com.ssafy.moabang.data.model.database.AppDatabase
import com.ssafy.moabang.data.model.dto.Cafe
import com.ssafy.moabang.src.retrofitInterface.cafeService
import java.lang.IllegalStateException

const val DB_NAME = "moabang.db"

class Repository private constructor(context: Context) {

    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DB_NAME
    ).build()

    private val cafeDao = database.cafeDao()

    suspend fun getAllCafe(): List<Cafe> =
        database.withTransaction {
            cafeDao.getAllCafe()
        }

    suspend fun getCafeByIsland(island: String): List<Cafe> =
        database.withTransaction {
            cafeDao.getCafeByIsland(island)
        }

    suspend fun getCafeByIslandSi(island: String, si: String): List<Cafe> =
        database.withTransaction {
            cafeDao.getCafeByIslandSi(island, si)
        }

    suspend fun getCafeByName(name: String): List<Cafe> =
        database.withTransaction {
            cafeDao.getCafeByName(name)
        }

    suspend fun insertCafe(cafe: Cafe) =
        database.withTransaction {
            cafeDao.insertCafe(cafe)
        }

    suspend fun insertCafes(cafeList: List<Cafe>) =
        database.withTransaction {
            cafeDao.insertCafes(cafeList)
        }

    suspend fun clearAll() =
        database.withTransaction {
            cafeDao.clearAll()
        }

    companion object {
        private var instance: Repository? = null
        fun initialize(context: Context) {
            if (instance == null) {
                instance = Repository(context)
            }
        }

        fun get(): Repository {
            if(instance == null){
                Log.d("AAAAA", "instance not init")
            }else{
                Log.d("AAAAA", "instance init")
            }
            return instance ?: throw IllegalStateException("초기화 안 됨")
        }
    }
}