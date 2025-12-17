package com.example.marketplace

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.marketplace.BaseDeDatos.CompraDao
import com.example.marketplace.BaseDeDatos.MarketplaceDataBase
import com.example.marketplace.adaptadores.CompraAdapter
import com.example.marketplace.databinding.ActivityHistorialComprasBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.core.view.GravityCompat
import com.google.firebase.auth.FirebaseAuth

class HistorialCompras : AppCompatActivity() {

    private lateinit var binding: ActivityHistorialComprasBinding
    private lateinit var compraDao: CompraDao
    private lateinit var adapter: CompraAdapter

    companion object {
        val DATABASE_NAME: String = "MARKETPLACE_DATABASE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistorialComprasBinding.inflate(layoutInflater)
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


        val db = Room.databaseBuilder(this, MarketplaceDataBase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

        compraDao = db.compraDao()

        adapter = CompraAdapter()
        binding.rvCompras.layoutManager = LinearLayoutManager(this)
        binding.rvCompras.adapter = adapter

        adapter.setOnClick { compra ->
            val i = Intent(this, DetalleCompra::class.java)
            i.putExtra("compraId", compra.id)
            i.putExtra("fecha", compra.fecha)
            i.putExtra("total", compra.total)
            startActivity(i)
        }

        cargarCompras()

        binding.btnPerfil.setOnClickListener {
            val intentPerfil = Intent(this, PerfilUsuario::class.java)
            startActivity(intentPerfil)
        }
        binding.btnCasa.setOnClickListener {
            val intentCasa = Intent(this, HomeProductos::class.java)
            startActivity(intentCasa)
        }
        binding.btnCarrito.setOnClickListener {
            val intentCarrito = Intent(this, Carrito::class.java)
            startActivity(intentCarrito)
        }
    }

    private fun cargarCompras() {
        GlobalScope.launch(Dispatchers.IO) {
            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            val lista = compraDao.getAllCompras(uid)
            withContext(Dispatchers.Main) {
                adapter.addDataCards(lista)
            }
        }
    }
}
