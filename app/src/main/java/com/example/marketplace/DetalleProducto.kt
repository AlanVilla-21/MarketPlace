package com.example.marketplace

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.marketplace.databinding.ActivityDetalleProductoBinding
import com.example.marketplace.BaseDeDatos.CarritoDao
import com.example.marketplace.BaseDeDatos.CarritoItemRoom
import com.example.marketplace.BaseDeDatos.MarketplaceDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalleProducto : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleProductoBinding
    private lateinit var carritoDao: CarritoDao

    companion object {
        val DATABASE_NAME: String = "MARKETPLACE_DATABASE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(
            this,
            MarketplaceDataBase::class.java,
            DATABASE_NAME
        ).build()

        carritoDao = db.carritoDao()

        val id = intent.getIntExtra("id", 0)
        val nombre = intent.getStringExtra("nombre") ?: ""
        val descripcion = intent.getStringExtra("descripcion") ?: ""
        val precio = intent.getDoubleExtra("precio", 0.0)
        val imagen = intent.getStringExtra("imagen") ?: ""

        binding.tvNombreDetalle.text = nombre
        binding.tvDescripcionDetalle.text = descripcion
        binding.tvPrecioDetalle.text = "Bs. $precio"

        val idImagen = resources.getIdentifier(imagen, "drawable", packageName)
        binding.imgProductoDetalle.setImageResource(idImagen)

        binding.btnAgregarCarrito.setOnClickListener {
            agregarAlCarrito(id, nombre, precio, imagen)
        }

        binding.btnIrCarrito.setOnClickListener {
            startActivity(Intent(this, Carrito::class.java))
        }
    }

    private fun agregarAlCarrito(idProducto: Int, nombre: String, precio: Double, imagen: String) {
        GlobalScope.launch(Dispatchers.IO) {

            val existente = carritoDao.getByIdProducto(idProducto)

            if (existente == null) {
                val nuevo = CarritoItemRoom(
                    0,
                    idProducto,
                    nombre,
                    precio,
                    1,
                    imagen
                )
                carritoDao.insertAll(nuevo)
            } else {
                carritoDao.updateCantidad(existente.id, existente.cantidad + 1)
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(this@DetalleProducto, "Agregado al carrito", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
