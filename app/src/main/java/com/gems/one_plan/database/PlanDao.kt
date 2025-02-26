package com.gems.one_plan.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanDao {
    @Query("SELECT * FROM Plans")
    fun getAll(): Flow<List<Plans>>

    @Insert
    fun insert(plan: Plans)

    @Update
    fun update(plan: Plans)

    @Delete
    fun delete(plan: Plans)

    @Query("SELECT * FROM Plans WHERE id = :id")
    fun getPlanById(id: Int): Flow<Plans>
}