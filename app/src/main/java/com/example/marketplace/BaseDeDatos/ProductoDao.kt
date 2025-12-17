package com.example.marketplace.BaseDeDatos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductoDao {

    @Query("SELECT * FROM ProductoRoom")
    fun getAll(): List<ProductoRoom>

    @Query("SELECT * FROM ProductoRoom WHERE categoria = :categoria")
    fun getByCategoria(categoria: String): List<ProductoRoom>

    @Query("SELECT * FROM ProductoRoom WHERE nombre LIKE '%' || :texto || '%'")
    fun buscarPorNombre(texto: String): List<ProductoRoom>

    @Query("SELECT imagen FROM ProductoRoom")
    fun getAllImagenes(): List<String>

    @Insert
    fun insertAll(vararg productos: ProductoRoom)
}
