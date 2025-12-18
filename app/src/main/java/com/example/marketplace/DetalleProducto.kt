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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalleProducto : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleProductoBinding
    private lateinit var carritoDao: CarritoDao

    companion object {
        val DATABASE_NAME: String = "MARKETPLACE_DATABASE"

        val EXTRA_ID = "EXTRA_ID"
        val EXTRA_NOMBRE = "EXTRA_NOMBRE"
        val EXTRA_DESCRIPCION = "EXTRA_DESCRIPCION"
        val EXTRA_PRECIO = "EXTRA_PRECIO"
        val EXTRA_IMAGEN = "EXTRA_IMAGEN"
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

        val id = intent.getIntExtra(EXTRA_ID, 0)
        val nombre = intent.getStringExtra(EXTRA_NOMBRE) ?: ""
        val descripcion = intent.getStringExtra(EXTRA_DESCRIPCION) ?: ""
        val precio = intent.getDoubleExtra(EXTRA_PRECIO, 0.0)
        val imagen = intent.getStringExtra(EXTRA_IMAGEN) ?: ""


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
            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            val existente = carritoDao.getByIdProducto(uid,idProducto)

            if (existente == null) {
                val nuevo = CarritoItemRoom(
                    0,
                    uid,
                    idProducto,
                    nombre,
                    precio,
                    1,
                    imagen
                )
                carritoDao.insertAll(nuevo)
            } else {
                carritoDao.updateCantidad(uid, existente.id, existente.cantidad + 1)
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(this@DetalleProducto, "Agregado al carrito", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
