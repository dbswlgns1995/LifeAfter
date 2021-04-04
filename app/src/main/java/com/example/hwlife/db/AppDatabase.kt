package com.example.hwlife.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hwlife.model.HWMain
import com.example.hwlife.model.HWSub

@Database(entities = [HWMain::class, HWSub::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun hwMainDao(): HWMainDao
    abstract fun hwSubDao() : HWSubDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context,
                        AppDatabase::class.java,
                        "hwmain_db")
                        .build()
                }
            }
            return INSTANCE
        }
    }
}