package com.example.marketplace

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.room.Room
import com.example.marketplace.BaseDeDatos.MarketplaceDataBase
import com.example.marketplace.BaseDeDatos.ProductoDao
import com.example.marketplace.BaseDeDatos.ProductoRoom
import com.example.marketplace.databinding.ActivityVenderProductosBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VenderProductos : AppCompatActivity() {

    private lateinit var binding: ActivityVenderProductosBinding
    private lateinit var productoDao: ProductoDao
    private var listaImagenes: List<String> = listOf()

    companion object {
        val DATABASE_NAME: String = "MARKETPLACE_DATABASE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVenderProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Inicializar Room/Dao ANTES de usarlo
        val db = Room.databaseBuilder(this, MarketplaceDataBase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

        productoDao = db.productoDao()

        // ✅ Ahora sí cargar imágenes desde DB
        cargarSpinnerImagenes()

        binding.Menu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> startActivity(Intent(this, HomeProductos::class.java))
                R.id.nav_vender -> { /* ya estás en vender */ }
                R.id.nav_compras -> startActivity(Intent(this, HistorialCompras::class.java))
                R.id.nav_categorias -> startActivity(Intent(this, categorias::class.java))
                R.id.nav_perfil -> startActivity(Intent(this, PerfilUsuario::class.java))
                R.id.nav_logout -> {
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this, LoginUsuario::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        binding.btnCasa.setOnClickListener {
            startActivity(Intent(this, HomeProductos::class.java))
        }
        binding.btnCarrito.setOnClickListener {
            startActivity(Intent(this, Carrito::class.java))
        }
        binding.btnPerfil.setOnClickListener {
            startActivity(Intent(this, PerfilUsuario::class.java))
        }

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

        // Si todavía no hay imágenes en DB, dejamos un fallback para que puedas elegir igual
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

    private fun cargarSpinnerImagenes() {
        GlobalScope.launch(Dispatchers.IO) {
            val imagenes = productoDao.getAllImagenes()
            listaImagenes = imagenes.distinct().sorted()

            withContext(Dispatchers.Main) {
                if (listaImagenes.isNotEmpty()) {
                    binding.spImagen.adapter = ArrayAdapter(
                        this@VenderProductos,
                        android.R.layout.simple_spinner_dropdown_item,
                        listaImagenes
                    )

                    val nombreDrawable = listaImagenes[0]
                    val idImagen = resources.getIdentifier(nombreDrawable, "drawable", packageName)
                    if (idImagen != 0) binding.imgPreview.setImageResource(idImagen)

                    binding.spImagen.onItemSelectedListener =
                        object : android.widget.AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: android.widget.AdapterView<*>,
                                view: android.view.View?,
                                position: Int,
                                id: Long
                            ) {
                                val nombre = listaImagenes[position]
                                val imgId = resources.getIdentifier(nombre, "drawable", packageName)
                                if (imgId != 0) binding.imgPreview.setImageResource(imgId)
                            }

                            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {}
                        }
                }
            }
        }
    }
}
