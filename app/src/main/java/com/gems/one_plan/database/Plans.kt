package com.gems.one_plan.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gems.one_plan.common.AppHelper

@Entity
data class Plans (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "text")
    val text: String = "",
    @ColumnInfo(name = "deadline")
    val deadline: String ?= null,
    @ColumnInfo(name = "hour")
    val hour : String ?= null,
    @ColumnInfo(name = "done")
    var done: Boolean = false,
    @ColumnInfo(name = "importance")
    val importance: Int = 0,
    @ColumnInfo(name = "lastEdited")
    val date: String = AppHelper.currentDate()
)