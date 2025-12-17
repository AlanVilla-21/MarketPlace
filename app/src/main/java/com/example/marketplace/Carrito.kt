package com.example.marketplace

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.marketplace.BaseDeDatos.CarritoDao
import com.example.marketplace.BaseDeDatos.CompraDao
import com.example.marketplace.BaseDeDatos.CompraDetalleRoom
import com.example.marketplace.BaseDeDatos.CompraRoom
import com.example.marketplace.BaseDeDatos.MarketplaceDataBase
import com.example.marketplace.adaptadores.CarritoAdapter
import com.example.marketplace.databinding.ActivityCarritoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.core.view.GravityCompat
import com.google.firebase.auth.FirebaseAuth

class Carrito : AppCompatActivity() {

    private lateinit var binding: ActivityCarritoBinding
    private lateinit var carritoDao: CarritoDao
    private lateinit var compraDao: CompraDao
    private lateinit var adapter: CarritoAdapter

    companion object {
        val DATABASE_NAME: String = "MARKETPLACE_DATABASE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarritoBinding.inflate(layoutInflater)
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

        carritoDao = db.carritoDao()
        compraDao = db.compraDao()

        adapter = CarritoAdapter()
        binding.rvCarrito.layoutManager = LinearLayoutManager(this)
        binding.rvCarrito.adapter = adapter

        cargarCarrito()

        binding.btnComprar.setOnClickListener {
            realizarCompra()
        }
    }

    private fun cargarCarrito() {
        GlobalScope.launch(Dispatchers.IO) {
            val lista = carritoDao.getAll()

            var total = 0.0
            for (item in lista) {
                total += item.precio * item.cantidad
            }

            withContext(Dispatchers.Main) {
                adapter.addDataCards(lista)
                binding.tvTotal.text = "Total: Bs. $total"
            }
        }
    }

    private fun realizarCompra() {
        GlobalScope.launch(Dispatchers.IO) {

            val lista = carritoDao.getAll()
            if (lista.isEmpty()) {
                return@launch
            }

            var total = 0.0
            for (item in lista) {
                total += item.precio * item.cantidad
            }

            val fecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

            val compraId = compraDao.insertCompra(
                CompraRoom(0, fecha, total)
            ).toInt()

            for (item in lista) {
                val detalle = CompraDetalleRoom(
                    0,
                    compraId,
                    item.nombre,
                    item.precio,
                    item.cantidad,
                    item.imagen
                )
                compraDao.insertDetalles(detalle)
            }

            carritoDao.deleteAll()

            withContext(Dispatchers.Main) {
                startActivity(Intent(this@Carrito, PagoTarjeta::class.java))
                finish()
            }
        }
    }
}
