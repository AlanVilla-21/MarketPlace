package com.example.marketplace.BaseDeDatos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CarritoDao {

    @Query("SELECT * FROM CarritoItemRoom WHERE uid = :uid")
    fun getAll(uid: String): List<CarritoItemRoom>

    @Insert
    fun insertAll(vararg productos: CarritoItemRoom)

    @Query("DELETE FROM CarritoItemRoom WHERE uid = :uid")
    fun deleteAll(uid: String)

    @Query("SELECT * FROM CarritoItemRoom WHERE uid = :uid AND idProducto = :idProd LIMIT 1")
    fun getByIdProducto(uid: String, idProd: Int): CarritoItemRoom?

    @Query("UPDATE CarritoItemRoom SET cantidad = :nuevaCantidad WHERE uid = :uid AND id = :idItem")
    fun updateCantidad(uid: String, idItem: Int, nuevaCantidad: Int)
}
