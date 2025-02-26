package com.gems.one_plan.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Plans::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun planDao(): PlanDao

    companion object {
        const val DATABASE_NAME = "plans_database"
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null){
                instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }
            return instance!!
        }
    }

}