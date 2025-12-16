package com.example.marketplace.BaseDeDatos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CompraRoom")
class CompraRoom(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "fecha") val fecha: String,
    @ColumnInfo(name = "total") val total: Double
)
