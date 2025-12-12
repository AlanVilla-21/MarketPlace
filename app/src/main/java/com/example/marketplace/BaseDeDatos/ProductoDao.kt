package com.example.marketplace.BaseDeDatos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductoDao {

    @Query("SELECT * FROM productoroom")
    fun getAll(): List<ProductoRoom>

    @Query("SELECT * FROM productoroom WHERE categoria = :categoria")
    fun getByCategori(categoria: Int): List<ProductoRoom>

    @Query("SELECT * FROM productoroom WHERE mombre LIKE '%' || :texto || '%'")
    fun getByNombre(texto: String): List<ProductoRoom>

    @Insert
    fun insertAll(vararg productos: ProductoRoom)
}