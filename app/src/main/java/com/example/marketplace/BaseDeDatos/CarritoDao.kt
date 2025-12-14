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

    @Query("SELECT * FROM carritoitemroom WHERE idProducto = :idProd LIMIT 1")
    fun getByIdProducto(idProd: Int): CarritoItemRoom?

    @Query("UPDATE carritoitemroom SET cantidad = :nuevaCantidad WHERE id = :idItem")
    fun updateCantidad(idItem: Int, nuevaCantidad: Double)

}