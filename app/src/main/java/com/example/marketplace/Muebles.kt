package com.example.marketplace

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.marketplace.BaseDeDatos.MarketplaceDataBase
import com.example.marketplace.BaseDeDatos.ProductoDao
import com.example.marketplace.adaptadores.ProductoAdapter
import com.example.marketplace.databinding.ActivityMueblesBinding
import com.example.marketplace.databinding.ActivityVehiculosBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Muebles : AppCompatActivity() {

    private lateinit var binding: ActivityMueblesBinding
    private lateinit var productoDao: ProductoDao
    private lateinit var adapter: ProductoAdapter

    companion object {
        val DATABASE_NAME: String = "MARKETPLACE_DATABASE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMueblesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Menu.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }


        val db = Room.databaseBuilder(
            this,
            MarketplaceDataBase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()

        productoDao = db.productoDao()

        adapter = ProductoAdapter()
        binding.rvProductosCategoria.layoutManager = LinearLayoutManager(this)
        binding.rvProductosCategoria.adapter = adapter

        adapter.setOnClick { producto ->
            val i = Intent(this, DetalleProducto::class.java)
            i.putExtra("id", producto.id)
            i.putExtra("categoria", producto.categoria)
            i.putExtra("nombre", producto.nombre)
            i.putExtra("descripcion", producto.descripcion)
            i.putExtra("precio", producto.precio)
            i.putExtra("imagen", producto.imagen)
            startActivity(i)
        }

        cargarCategoria("Muebles")
    }

    private fun cargarCategoria(cat: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val lista = productoDao.getByCategoria(cat)
            withContext(Dispatchers.Main) {
                adapter.addDataCards(lista)
            }
        }
    }
}
