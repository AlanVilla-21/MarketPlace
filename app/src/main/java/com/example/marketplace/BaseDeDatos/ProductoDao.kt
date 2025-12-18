package com.example.marketplace.BaseDeDatos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
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

    @Query("SELECT COUNT(*) FROM ProductoRoom WHERE categoria = :categoria")
    fun countByCategoria(categoria: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg productos: ProductoRoom)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLista(productos: List<ProductoRoom>)
}
