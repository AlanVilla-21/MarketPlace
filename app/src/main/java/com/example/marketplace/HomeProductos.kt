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
import androidx.core.view.GravityCompat
import com.google.firebase.auth.FirebaseAuth
import androidx.recyclerview.widget.GridLayoutManager

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

        binding.Menu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> { /* ya estás en Home */ }
                R.id.nav_vender -> startActivity(Intent(this, VenderProductos::class.java))
                R.id.nav_compras -> startActivity(Intent(this, HistorialCompras::class.java))
                R.id.nav_categorias -> startActivity(Intent(this, categorias::class.java))
                R.id.nav_perfil -> startActivity(Intent(this, PerfilUsuario::class.java))
                R.id.nav_logout -> {
                    FirebaseAuth.getInstance().signOut()
                    val i = Intent(this, LoginUsuario::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                    finish()
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        val db = Room.databaseBuilder(this, MarketplaceDataBase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

        productoDao = db.productoDao()

        adapter = ProductoAdapter()
        binding.rvProductos.layoutManager = GridLayoutManager(this, 2)
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

        seedProductosSiHaceFalta()

        binding.etBuscar.doAfterTextChanged {
            val nombre = it.toString()
            buscar(nombre)
        }

        binding.btnCategorias.setOnClickListener {
            startActivity(Intent(this, categorias::class.java))
        }

        binding.btnPerfil.setOnClickListener {
            startActivity(Intent(this, PerfilUsuario::class.java))
        }

        binding.btnCasa.setOnClickListener {
            cargarProductos()
        }

        binding.btnCarrito.setOnClickListener {
            startActivity(Intent(this, Carrito::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        cargarProductos()
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

            val lista = ArrayList<ProductoRoom>()

            if (productoDao.countByCategoria("Vehiculos") == 0) {
                lista.add(ProductoRoom(0,"Vehiculos","Toyota Corolla","Vehículo en buen estado",1000.0,"toyota_corolla"))
                lista.add(ProductoRoom(0,"Vehiculos","Suzuki Swift","Económico y práctico",1200.0,"suzuki_swift"))
                lista.add(ProductoRoom(0,"Vehiculos","Nissan Note","Ideal para ciudad",900.0,"nissan_note"))
                lista.add(ProductoRoom(0,"Vehiculos","Honda Civic","Cómodo y rápido",1500.0,"honda_civic"))
                lista.add(ProductoRoom(0,"Vehiculos","Moto Yamaha","Moto urbana",700.0,"moto_yamaha"))
                lista.add(ProductoRoom(0,"Vehiculos","Bicicleta MTB","Todo terreno",300.0,"bicicleta_mtb"))
            }

            if (productoDao.countByCategoria("Ropa de varón") == 0) {
                lista.add(ProductoRoom(0,"Ropa de varón","Zapatos Oxford","Talla 42",350.0,"zapatos_oxford"))
                lista.add(ProductoRoom(0,"Ropa de varón","Camisa Formal","Camisa blanca",120.0,"camisa_formal"))
                lista.add(ProductoRoom(0,"Ropa de varón","Pantalón Jeans","Jeans azul",100.0,"pantalon_jeans"))
                lista.add(ProductoRoom(0,"Ropa de varón","Chaqueta","Chaqueta negra",200.0,"chaqueta_negra"))
                lista.add(ProductoRoom(0,"Ropa de varón","Polera","Polera deportiva",80.0,"polera_deportiva"))
                lista.add(ProductoRoom(0,"Ropa de varón","Zapatillas","Urbanas",180.0,"zapatillas_urbanas"))
            }

            if (productoDao.countByCategoria("Ropa de Mujer") == 0) {
                lista.add(ProductoRoom(0,"Ropa de Mujer","Vestido","Vestido casual",180.0,"vestido"))
                lista.add(ProductoRoom(0,"Ropa de Mujer","Blusa","Blusa cómoda",90.0,"blusa"))
                lista.add(ProductoRoom(0,"Ropa de Mujer","Falda","Falda negra",110.0,"falda"))
                lista.add(ProductoRoom(0,"Ropa de Mujer","Tacones","Tacones rojos",200.0,"tacones"))
                lista.add(ProductoRoom(0,"Ropa de Mujer","Bolso","Bolso elegante",150.0,"bolso"))
                lista.add(ProductoRoom(0,"Ropa de Mujer","Chaqueta Mujer","Abrigo ligero",220.0,"chaqueta_mujer"))
            }

            if (productoDao.countByCategoria("Electronica") == 0) {
                lista.add(ProductoRoom(0,"Electronica","Audífonos","Bluetooth",120.0,"audifonos"))
                lista.add(ProductoRoom(0,"Electronica","Mouse","Inalámbrico",80.0,"mouse"))
                lista.add(ProductoRoom(0,"Electronica","Teclado","Mecánico",200.0,"teclado"))
                lista.add(ProductoRoom(0,"Electronica","Parlante","Portátil",160.0,"parlante"))
                lista.add(ProductoRoom(0,"Electronica","Celular","Gama media",1500.0,"celular"))
                lista.add(ProductoRoom(0,"Electronica","Laptop","Para estudio",3500.0,"laptop"))
            }

            if (productoDao.countByCategoria("Muebles") == 0) {
                lista.add(ProductoRoom(0,"Muebles","Silla","Silla cómoda",120.0,"silla"))
                lista.add(ProductoRoom(0,"Muebles","Mesa","Mesa de comedor",400.0,"mesa"))
                lista.add(ProductoRoom(0,"Muebles","Sofá","Sofá 3 plazas",900.0,"sofa"))
                lista.add(ProductoRoom(0,"Muebles","Cama","Cama 2 plazas",1200.0,"cama"))
                lista.add(ProductoRoom(0,"Muebles","Lámpara","Lámpara de mesa",90.0,"lampara"))
                lista.add(ProductoRoom(0,"Muebles","Estante","Estante madera",250.0,"estante"))
            }

            if (productoDao.countByCategoria("Alquileres") == 0) {
                lista.add(ProductoRoom(0,"Alquileres","Departamento","Sopocachi",2500.0,"departamento"))
                lista.add(ProductoRoom(0,"Alquileres","Habitación","Centro",900.0,"habitacion"))
                lista.add(ProductoRoom(0,"Alquileres","Casa","Achumani",4000.0,"casa"))
                lista.add(ProductoRoom(0,"Alquileres","Oficina","Obrajes",3000.0,"oficina"))
                lista.add(ProductoRoom(0,"Alquileres","Garaje","Cochera",400.0,"garaje"))
                lista.add(ProductoRoom(0,"Alquileres","Local","Comercial",3500.0,"local"))
            }

            if (productoDao.countByCategoria("Instrumentos") == 0) {
                lista.add(ProductoRoom(0,"Instrumentos","Guitarra","Acústica",500.0,"guitarra"))
                lista.add(ProductoRoom(0,"Instrumentos","Teclado","61 teclas",900.0,"teclado_musical"))
                lista.add(ProductoRoom(0,"Instrumentos","Batería","Batería básica",2500.0,"bateria"))
                lista.add(ProductoRoom(0,"Instrumentos","Violín","Para estudiantes",700.0,"violin"))
                lista.add(ProductoRoom(0,"Instrumentos","Ukelele","Pequeño",250.0,"ukelele"))
                lista.add(ProductoRoom(0,"Instrumentos","Micrófono","Con base",180.0,"microfono"))
            }

            if (productoDao.countByCategoria("Ropa de Bebe") == 0) {
                lista.add(ProductoRoom(0,"Ropa de Bebe","Body","Algodón",60.0,"body_bebe"))
                lista.add(ProductoRoom(0,"Ropa de Bebe","Pijama","Calientito",80.0,"pijama_bebe"))
                lista.add(ProductoRoom(0,"Ropa de Bebe","Gorro","Suave",25.0,"gorro_bebe"))
                lista.add(ProductoRoom(0,"Ropa de Bebe","Zapatos Bebé","Pequeños",50.0,"zapatos_bebe"))
                lista.add(ProductoRoom(0,"Ropa de Bebe","Manta","Polar",90.0,"manta_bebe"))
                lista.add(ProductoRoom(0,"Ropa de Bebe","Conjunto","2 piezas",110.0,"conjunto_bebe"))
            }

            if (productoDao.countByCategoria("Deportes") == 0) {
                lista.add(ProductoRoom(0,"Deportes","Balón","Fútbol",80.0,"balon"))
                lista.add(ProductoRoom(0,"Deportes","Guantes","Box",120.0,"guantes_box"))
                lista.add(ProductoRoom(0,"Deportes","Mancuernas","Par 10kg",200.0,"mancuernas"))
                lista.add(ProductoRoom(0,"Deportes","Raqueta","Tenis",250.0,"raqueta"))
                lista.add(ProductoRoom(0,"Deportes","Casco","Bici",150.0,"casco"))
                lista.add(ProductoRoom(0,"Deportes","Cuerda","Saltar",40.0,"cuerda"))
            }

            if (lista.isNotEmpty()) {
                productoDao.insertLista(lista) // ✅ ignora duplicados por UNIQUE + IGNORE
            }

            withContext(Dispatchers.Main) {
                cargarProductos()
            }
        }
    }
}
