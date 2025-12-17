package com.example.marketplace

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.marketplace.BaseDeDatos.MarketplaceDataBase
import com.example.marketplace.BaseDeDatos.ProductoDao
import com.example.marketplace.adaptadores.ProductoAdapter
import com.example.marketplace.databinding.ActivityInstrumentosBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.core.view.GravityCompat
import com.google.firebase.auth.FirebaseAuth

class Instrumentos : AppCompatActivity() {

    private lateinit var binding: ActivityInstrumentosBinding
    private lateinit var productoDao: ProductoDao
    private lateinit var adapter: ProductoAdapter

    companion object {
        val DATABASE_NAME: String = "MARKETPLACE_DATABASE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstrumentosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Menu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> startActivity(Intent(this, HomeProductos::class.java))
                R.id.nav_vender -> startActivity(Intent(this, VenderProductos::class.java))
                R.id.nav_compras -> startActivity(Intent(this, HistorialCompras::class.java))
                R.id.nav_categorias -> startActivity(Intent(this, categorias::class.java))
                R.id.nav_perfil -> startActivity(Intent(this, PerfilUsuario::class.java))
                R.id.nav_logout -> {
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this, LoginUsuario::class.java))
                    finish()
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
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

        cargarCategoria("Instrumentos")
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
