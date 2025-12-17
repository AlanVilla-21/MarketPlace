package com.example.marketplace.BaseDeDatos

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ProductoRoom::class, CarritoItemRoom::class, CompraRoom::class, CompraDetalleRoom::class],
    version = 6
)
abstract class MarketplaceDataBase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
    abstract fun carritoDao(): CarritoDao
    abstract fun compraDao(): CompraDao
}

