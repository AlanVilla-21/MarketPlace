package com.example.marketplace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.marketplace.BaseDeDatos.CompraDao
import com.example.marketplace.BaseDeDatos.MarketplaceDataBase
import com.example.marketplace.adaptadores.CompraDetalleAdapter
import com.example.marketplace.databinding.ActivityDetalleCompraBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalleCompra : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleCompraBinding
    private lateinit var compraDao: CompraDao
    private lateinit var adapter: CompraDetalleAdapter

    companion object {
        val DATABASE_NAME: String = "MARKETPLACE_DATABASE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleCompraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val compraId = intent.getIntExtra("compraId", 0)
        val fecha = intent.getStringExtra("fecha") ?: ""
        val total = intent.getDoubleExtra("total", 0.0)

        binding.tvFechaDetalle.text = fecha
        binding.tvTotalDetalle.text = "Total: Bs. $total"

        val db = Room.databaseBuilder(this, MarketplaceDataBase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

        compraDao = db.compraDao()

        adapter = CompraDetalleAdapter()
        binding.rvDetalleCompra.layoutManager = LinearLayoutManager(this)
        binding.rvDetalleCompra.adapter = adapter

        cargarDetalles(compraId)
    }

    private fun cargarDetalles(compraId: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            val lista = compraDao.getDetalles(uid ,compraId)
            withContext(Dispatchers.Main) {
                adapter.addDataCards(lista)
            }
        }
    }
}
