package com.example.marketplace

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.marketplace.BaseDeDatos.MarketplaceDataBase
import com.example.marketplace.BaseDeDatos.ProductoDao
import com.example.marketplace.BaseDeDatos.ProductoRoom
import com.example.marketplace.databinding.ActivityVenderProductosBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VenderProductos : AppCompatActivity() {

    private lateinit var binding: ActivityVenderProductosBinding
    private lateinit var productoDao: ProductoDao

    companion object {
        val DATABASE_NAME: String = "MARKETPLACE_DATABASE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVenderProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCasa.setOnClickListener {
            val intentHomeProductos = Intent(this, HomeProductos::class.java)
            startActivity(intentHomeProductos)
        }
        binding.btnCarrito.setOnClickListener {
            val intentCarrito = Intent(this, Carrito::class.java)
            startActivity(intentCarrito)
        }
        binding.btnPerfil.setOnClickListener {
            val intentHistorial = Intent(this, PerfilUsuario::class.java)
            startActivity(intentHistorial)
        }

        val db = Room.databaseBuilder(this, MarketplaceDataBase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

        productoDao = db.productoDao()

        // Categorías exactamente como las usas en el seed y en los botones
        val categorias = listOf(
            "Vehiculos",
            "Ropa de Mujer",
            "Electronica",
            "Muebles",
            "Ropa de varón",
            "Alquileres",
            "Instrumentos",
            "Ropa de Bebe",
            "Deportes"
        )

        // Imágenes: nombres de drawables (DEBEN existir en /drawable)
        // Puedes reemplazar esta lista por tus drawables reales.
        val imagenes = listOf(
            "toyota_corolla",
            "suzuki_swift",
            "nissan_note",
            "honda_civic",
            "moto_yamaha",
            "bicicleta_mtb",
            "camisa_formal",
            "zapatos_oxford",
            "zapatillas_urbanas"
        )

        binding.spCategoria.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categorias
        )

        binding.spImagen.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            imagenes
        )

        binding.btnGuardar.setOnClickListener {
            guardarProducto()
        }
    }

    private fun guardarProducto() {
        val nombre = binding.etNombre.text.toString().trim()
        val descripcion = binding.etDescripcion.text.toString().trim()
        val precioStr = binding.etPrecio.text.toString().trim()

        val categoria = binding.spCategoria.selectedItem.toString()
        val imagen = binding.spImagen.selectedItem.toString()

        if (nombre.isEmpty() || descripcion.isEmpty() || precioStr.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val precio = precioStr.toDoubleOrNull()
        if (precio == null || precio <= 0) {
            Toast.makeText(this, "Precio inválido", Toast.LENGTH_SHORT).show()
            return
        }

        val nuevo = ProductoRoom(
            0,
            categoria,
            nombre,
            descripcion,
            precio,
            imagen
        )

        GlobalScope.launch(Dispatchers.IO) {
            productoDao.insertAll(nuevo)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@VenderProductos, "Producto publicado", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@VenderProductos, HomeProductos::class.java))
                finish()
            }
        }
    }
}
