package com.example.marketplace.BaseDeDatos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CompraDao {

    @Query("SELECT * FROM CompraRoom ORDER BY id DESC")
    fun getAllCompras(): List<CompraRoom>

    @Query("SELECT * FROM CompraDetalleRoom WHERE compraId = :compraId")
    fun getDetalles(compraId: Int): List<CompraDetalleRoom>

    @Insert
    fun insertCompra(compra: CompraRoom): Long

    @Insert
    fun insertDetalles(vararg detalles: CompraDetalleRoom)
}
