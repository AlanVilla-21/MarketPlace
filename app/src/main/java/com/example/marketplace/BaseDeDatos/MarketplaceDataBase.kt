package com.example.marketplace.BaseDeDatos

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductoRoom::class, CarritoItemRoom::class], version = 1)
abstract class MarketplaceDataBase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
    abstract fun carritoDao(): CarritoDao
}
