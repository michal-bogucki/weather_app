package com.weatherapplication.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.weatherapplication.feature.cityweather.data.local.dao.WeatherDao
import com.weatherapplication.feature.searchcity.data.local.dao.SearchCityDao
import com.weatherapplication.feature.searchcity.data.local.model.SearchCityCached
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [SearchCityCached::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchCityDao(): SearchCityDao
    abstract fun weatherDao(): WeatherDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = init(context)
                INSTANCE = instance
                instance
            }
        }

        private fun init(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "Database"
            )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val database = getDatabase(context)
                        GlobalScope.launch { database.initDatabase(context) }
                    }
                })
                .build()
        }
    }

    suspend fun initDatabase(context: Context) {

    }

}