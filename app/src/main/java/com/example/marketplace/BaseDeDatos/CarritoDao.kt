package com.example.marketplace.BaseDeDatos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CarritoDao {
    @Query("SELECT * FROM carritoitemroom")
    fun getAll(): List<CarritoItemRoom>

    @Insert
    fun insertAll(vararg productos: CarritoItemRoom)

    @Query("DELETE FROM carritoitemroom")
    fun deleteAll()
}