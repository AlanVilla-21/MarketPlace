package com.example.marketplace

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.marketplace.BaseDeDatos.MarketplaceDataBase
import com.example.marketplace.BaseDeDatos.ProductoDao
import com.example.marketplace.BaseDeDatos.ProductoRoom
import com.example.marketplace.adaptadores.ProductoAdapter
import com.example.marketplace.databinding.ActivityHomeProductosBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeProductos : AppCompatActivity() {

    private lateinit var binding: ActivityHomeProductosBinding
    private lateinit var productoDao: ProductoDao
    private lateinit var adapter: ProductoAdapter

    companion object {
        val DATABASE_NAME: String = "MARKETPLACE_DATABASE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(
            this,
            MarketplaceDataBase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()

        productoDao = db.productoDao()

        adapter = ProductoAdapter()
        binding.rvProductos.adapter = adapter

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

        binding.rvProductos.layoutManager = LinearLayoutManager(this)
        binding.rvProductos.adapter = adapter

        seedProductosSiHaceFalta()

        binding.etBuscar.doAfterTextChanged {
            buscar(it.toString())
        }
    }

    private fun cargarProductos() {
        GlobalScope.launch(Dispatchers.IO) {
            val lista = productoDao.getAll()
            withContext(Dispatchers.Main) {
                adapter.addDataCards(lista)
            }
        }
    }

    private fun buscar(texto: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val lista = if (texto.trim().isEmpty()) {
                productoDao.getAll()
            } else {
                productoDao.buscarPorNombre(texto)
            }
            withContext(Dispatchers.Main) {
                adapter.addDataCards(lista)
            }
        }
    }

    private fun seedProductosSiHaceFalta() {
        GlobalScope.launch(Dispatchers.IO) {

            val existentes = productoDao.getAll()
            if (existentes.isNotEmpty()) {
                withContext(Dispatchers.Main) {
                    cargarProductos()
                }
                return@launch
            }

            val lista = ArrayList<ProductoRoom>()

            // Vehiculos (6)
            lista.add(ProductoRoom(0,"Vehiculos","Toyota Corolla","Vehículo en buen estado",1000.0,"toyota_corolla"))
            lista.add(ProductoRoom(0,"Vehiculos","Suzuki Swift","Económico y práctico",1200.0,"suzuki_swift"))
            lista.add(ProductoRoom(0,"Vehiculos","Nissan Note","Ideal para ciudad",900.0,"nissan_note"))
            lista.add(ProductoRoom(0,"Vehiculos","Honda Civic","Cómodo y rápido",1500.0,"honda_civic"))
            lista.add(ProductoRoom(0,"Vehiculos","Moto Yamaha","Moto urbana",700.0,"moto_yamaha"))
            lista.add(ProductoRoom(0,"Vehiculos","Bicicleta MTB","Todo terreno",300.0,"bicicleta_mtb"))

            // Ropa de varón (6)
            lista.add(ProductoRoom(0,"Ropa de varón","Zapatos Oxford","Talla 42",350.0,"zapatos_oxford"))
            lista.add(ProductoRoom(0,"Ropa de varón","Camisa Formal","Camisa blanca",120.0,"camisa_formal"))
            lista.add(ProductoRoom(0,"Ropa de varón","Pantalón Jeans","Jeans azul",100.0,"pantalon_jeans"))
            lista.add(ProductoRoom(0,"Ropa de varón","Chaqueta","Chaqueta negra",200.0,"chaqueta_negra"))
            lista.add(ProductoRoom(0,"Ropa de varón","Polera","Polera deportiva",80.0,"polera_deportiva"))
            lista.add(ProductoRoom(0,"Ropa de varón","Zapatillas","Urbanas",180.0,"zapatillas_urbanas"))

            for (p in lista) {
                productoDao.insertAll(p)
            }

            withContext(Dispatchers.Main) {
                cargarProductos()
            }
        }
    }
}
