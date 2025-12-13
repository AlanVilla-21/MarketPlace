package com.example.marketplace

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import android.content.Intent
import androidx.core.view.WindowInsetsCompat
import com.example.marketplace.databinding.ActivityCategoriasBinding

class categorias : AppCompatActivity() {
    private lateinit var binding: ActivityCategoriasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCategoriasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //intentinsets
        //shared preferences o room(implementacion)
        //base de datos si queremos implementar maximo hasta el viernes


        binding.btnVehiculos.setOnClickListener {
            val intentVehiculos = Intent(this, Vehiculos::class.java)
            startActivity(intentVehiculos)
        }
        binding.btnRopaDeMujer.setOnClickListener {
            val intentRopaMujer = Intent(this, RopaMujer::class.java)
            startActivity(intentRopaMujer)
        }
        binding.btnElectronica.setOnClickListener {
            val intentElectronica = Intent(this, Electronica::class.java)
            startActivity(intentElectronica)
        }
        binding.btnMuebles.setOnClickListener {
            val intentMuebles = Intent(this, Muebles::class.java)
            startActivity(intentMuebles)
        }
        binding.btnRopaDeVaron.setOnClickListener {
            val intentRopaVaron = Intent(this, RopaVaron::class.java)
            startActivity(intentRopaVaron)
        }
        binding.btnAlquileres.setOnClickListener {
            val intentAlquileres = Intent(this, Alquileres::class.java)
            startActivity(intentAlquileres)

        }
        binding.btnInstrumentos.setOnClickListener {
            val intentinstrumentos = Intent(this, Instrumentos::class.java)
            startActivity(intentinstrumentos)
        }
        binding.btnRopaDeBebe.setOnClickListener {
            val intentRopadebebe = Intent(this, RopaBebe::class.java)
            startActivity(intentRopadebebe)
        }
        binding.btndeportes.setOnClickListener {
            val intentDeportes = Intent(this, Deportes::class.java)
            startActivity(intentDeportes)
        }
    }
}