package com.example.miniproject1.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "allstores")
data class Store (
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0,
    val name: String = "",
    val description: String = "",
    val lat : Double = 0.0,
    val long : Double = 0.0,
    val radius: Double = 0.0
)