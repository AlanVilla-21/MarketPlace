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

        binding.btnCategorias.setOnClickListener {
            startActivity(Intent(this, categorias::class.java))
        }

        binding.btnPerfil.setOnClickListener {
            val intentPerfil = Intent(this, PerfilUsuario::class.java)
            startActivity(intentPerfil)
        }
        binding.btnCasa.setOnClickListener {
            val intentHomeProductos = Intent(this, HomeProductos::class.java)
            startActivity(intentHomeProductos)
        }
        binding.btnCarrito.setOnClickListener {
            val intentCarrito = Intent(this, Carrito::class.java)
            startActivity(intentCarrito)
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


// Ropa de Mujer (6)

            lista.add(ProductoRoom(0,"Ropa de Mujer","Vestido Casual","Vestido para uso diario",180.0,"ropa_mujer_vestido"))
            lista.add(ProductoRoom(0,"Ropa de Mujer","Blusa Elegante","Blusa formal",120.0,"ropa_mujer_blusa"))
            lista.add(ProductoRoom(0,"Ropa de Mujer","Falda Negra","Falda clásica",110.0,"ropa_mujer_falda"))
            lista.add(ProductoRoom(0,"Ropa de Mujer","Tacones","Zapatos de tacón",220.0,"ropa_mujer_tacones"))
            lista.add(ProductoRoom(0,"Ropa de Mujer","Chaqueta Mujer","Abrigo ligero",200.0,"ropa_mujer_chaqueta"))
            lista.add(ProductoRoom(0,"Ropa de Mujer","Bolso","Bolso de mano",160.0,"ropa_mujer_bolso"))


// Electronica (6)

            lista.add(ProductoRoom(0,"Electronica","Audífonos","Audífonos inalámbricos",150.0,"electronica_audifonos"))
            lista.add(ProductoRoom(0,"Electronica","Mouse Gamer","Mouse con luces",120.0,"electronica_mouse"))
            lista.add(ProductoRoom(0,"Electronica","Teclado Mecánico","Teclado mecánico",250.0,"electronica_teclado"))
            lista.add(ProductoRoom(0,"Electronica","Parlante Bluetooth","Parlante portátil",180.0,"electronica_parlante"))
            lista.add(ProductoRoom(0,"Electronica","Smartwatch","Reloj inteligente",300.0,"electronica_smartwatch"))
            lista.add(ProductoRoom(0,"Electronica","Cargador Rápido","Carga rápida",80.0,"electronica_cargador"))


// Muebles (6)

            lista.add(ProductoRoom(0,"Muebles","Sillón Café","Sillón cómodo",500.0,"muebles_sillon"))
            lista.add(ProductoRoom(0,"Muebles","Mesa de Centro","Mesa para sala",350.0,"muebles_mesa_centro"))
            lista.add(ProductoRoom(0,"Muebles","Silla","Silla moderna",200.0,"muebles_silla"))
            lista.add(ProductoRoom(0,"Muebles","Escritorio","Ideal para estudio",450.0,"muebles_escritorio"))
            lista.add(ProductoRoom(0,"Muebles","Estante","Estante de madera",300.0,"muebles_estante"))
            lista.add(ProductoRoom(0,"Muebles","Cama 2 Plazas","Cama cómoda",900.0,"muebles_cama"))


// Ropa de varón (6)

            lista.add(ProductoRoom(0,"Ropa de varón","Zapatos Oxford","Talla 42",350.0,"zapatos_oxford"))
            lista.add(ProductoRoom(0,"Ropa de varón","Camisa Formal","Camisa blanca",120.0,"camisa_formal"))
            lista.add(ProductoRoom(0,"Ropa de varón","Pantalón Jeans","Jeans azul",100.0,"pantalon_jeans"))
            lista.add(ProductoRoom(0,"Ropa de varón","Chaqueta","Chaqueta negra",200.0,"chaqueta_negra"))
            lista.add(ProductoRoom(0,"Ropa de varón","Polera","Polera deportiva",80.0,"polera_deportiva"))
            lista.add(ProductoRoom(0,"Ropa de varón","Zapatillas","Urbanas",180.0,"zapatillas_urbanas"))


// Alquileres (6)

            lista.add(ProductoRoom(0,"Alquileres","Departamento","Departamento amoblado",1200.0,"alquiler_depa"))
            lista.add(ProductoRoom(0,"Alquileres","Habitación","Habitación individual",600.0,"alquiler_habitacion"))
            lista.add(ProductoRoom(0,"Alquileres","Auto por día","Alquiler de auto",250.0,"alquiler_auto"))
            lista.add(ProductoRoom(0,"Alquileres","Salón de eventos","Para fiestas",800.0,"alquiler_salon"))
            lista.add(ProductoRoom(0,"Alquileres","Equipo de sonido","Sonido para eventos",300.0,"alquiler_sonido"))
            lista.add(ProductoRoom(0,"Alquileres","Proyector","Proyector para presentaciones",200.0,"alquiler_proyector"))


// Instrumentos (6)

            lista.add(ProductoRoom(0,"Instrumentos","Guitarra","Guitarra acústica",500.0,"instrumento_guitarra"))
            lista.add(ProductoRoom(0,"Instrumentos","Bajo Eléctrico","Bajo 4 cuerdas",900.0,"instrumento_bajo"))
            lista.add(ProductoRoom(0,"Instrumentos","Teclado","Teclado musical",1100.0,"instrumento_teclado"))
            lista.add(ProductoRoom(0,"Instrumentos","Batería","Batería completa",2500.0,"instrumento_bateria"))
            lista.add(ProductoRoom(0,"Instrumentos","Violín","Violín para estudiantes",700.0,"instrumento_violin"))
            lista.add(ProductoRoom(0,"Instrumentos","Micrófono","Micrófono profesional",220.0,"instrumento_microfono"))


// Ropa de Bebe (6)

            lista.add(ProductoRoom(0,"Ropa de Bebe","Body","Body de algodón",60.0,"bebe_body"))
            lista.add(ProductoRoom(0,"Ropa de Bebe","Pijama","Pijama suave",80.0,"bebe_pijama"))
            lista.add(ProductoRoom(0,"Ropa de Bebe","Zapatos Bebé","Zapatos pequeños",50.0,"bebe_zapatos"))
            lista.add(ProductoRoom(0,"Ropa de Bebe","Gorro","Gorro abrigado",35.0,"bebe_gorro"))
            lista.add(ProductoRoom(0,"Ropa de Bebe","Pantalón Bebé","Pantalón cómodo",55.0,"bebe_pantalon"))
            lista.add(ProductoRoom(0,"Ropa de Bebe","Chaqueta Bebé","Chaqueta abrigada",120.0,"bebe_chaqueta"))


// Deportes (6)

            lista.add(ProductoRoom(0,"Deportes","Balón de fútbol","Balón tamaño 5",90.0,"deportes_balon_futbol"))
            lista.add(ProductoRoom(0,"Deportes","Guantes de box","Guantes resistentes",160.0,"deportes_guantes_box"))
            lista.add(ProductoRoom(0,"Deportes","Mancuernas 10kg","Par de mancuernas",220.0,"deportes_mancuernas"))
            lista.add(ProductoRoom(0,"Deportes","Cuerda","Cuerda para saltar",40.0,"deportes_cuerda"))
            lista.add(ProductoRoom(0,"Deportes","Mat de yoga","Colchoneta",70.0,"deportes_mat_yoga"))
            lista.add(ProductoRoom(0,"Deportes","Raqueta","Raqueta deportiva",150.0,"deportes_raqueta"))

            for (p in lista) {
                productoDao.insertAll(p)
            }

            withContext(Dispatchers.Main) {
                cargarProductos()
            }
        }
    }
}
