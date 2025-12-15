package com.example.marketplace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.marketplace.BaseDeDatos.CarritoDao
import com.example.marketplace.BaseDeDatos.MarketplaceDataBase
import com.example.marketplace.adaptadores.CarritoAdapter
import com.example.marketplace.databinding.ActivityCarritoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Carrito : AppCompatActivity() {

    private lateinit var binding: ActivityCarritoBinding
    private lateinit var carritoDao: CarritoDao
    private lateinit var adapter: CarritoAdapter

    companion object {
        val DATABASE_NAME: String = "MARKETPLACE_DATABASE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarritoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(
            this,
            MarketplaceDataBase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()

        carritoDao = db.carritoDao()

        adapter = CarritoAdapter()

        binding.rvCarrito.layoutManager = LinearLayoutManager(this)
        binding.rvCarrito.adapter = adapter

        cargarCarrito()

        binding.btnVaciar.setOnClickListener {
            vaciarCarrito()
        }
    }

    private fun cargarCarrito() {
        GlobalScope.launch(Dispatchers.IO) {
            val lista = carritoDao.getAll()
            val total = lista.sumOf { it.precio * it.cantidad }

            withContext(Dispatchers.Main) {
                adapter.addDataCards(lista)
                binding.tvTotal.text = "Total: Bs. $total"
            }
        }
    }

    private fun vaciarCarrito() {
        GlobalScope.launch(Dispatchers.IO) {
            carritoDao.deleteAll()
            withContext(Dispatchers.Main) {
                cargarCarrito()
            }
        }
    }
}
