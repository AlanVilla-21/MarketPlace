package com.example.marketplace.BaseDeDatos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CompraDao {

    @Query("SELECT * FROM CompraRoom WHERE uid = :uid ORDER BY id DESC")
    fun getAllCompras(uid: String): List<CompraRoom>

    @Query("SELECT * FROM CompraDetalleRoom WHERE uid = :uid AND compraId = :compraId")
    fun getDetalles(uid: String, compraId: Int): List<CompraDetalleRoom>

    @Insert
    fun insertCompra(compra: CompraRoom): Long

    @Insert
    fun insertDetalles(vararg detalles: CompraDetalleRoom)
}
