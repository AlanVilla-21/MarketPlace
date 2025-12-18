package com.example.marketplace.BaseDeDatos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["categoria", "nombre"], unique = true)]
)
class ProductoRoom(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "categoria") val categoria: String,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "descripcion") val descripcion: String,
    @ColumnInfo(name = "precio") val precio: Double,
    @ColumnInfo(name = "imagen") val imagen: String
)
