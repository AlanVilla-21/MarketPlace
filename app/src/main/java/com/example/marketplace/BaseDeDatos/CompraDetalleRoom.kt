package com.example.marketplace.BaseDeDatos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CompraDetalleRoom")
class CompraDetalleRoom(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "uid") val uid: String,
    @ColumnInfo(name = "compraId") val compraId: Int,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "precio") val precio: Double,
    @ColumnInfo(name = "cantidad") val cantidad: Int,
    @ColumnInfo(name = "imagen") val imagen: String
)
