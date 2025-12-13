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
        binding.btnVehiculos.setOnClickListener {
            val intentRopaMujer = Intent(this, Vehiculos::class.java)
            startActivity(intentRopaMujer)
        }
        binding.btnVehiculos.setOnClickListener {
            val intentElectronica = Intent(this, Vehiculos::class.java)
            startActivity(intentElectronica)
        }
        binding.btnVehiculos.setOnClickListener {
            val intentMuebles = Intent(this, Vehiculos::class.java)
            startActivity(intentMuebles)
        }
        binding.btnVehiculos.setOnClickListener {
            val intentAlquileres = Intent(this, Vehiculos::class.java)
            startActivity(intentAlquileres)
        }
        binding.btnVehiculos.setOnClickListener {
            val intentRopadevaron = Intent(this, Vehiculos::class.java)
            startActivity(intentRopadevaron)

        }
        binding.btnVehiculos.setOnClickListener {
            val intentinstrumentos = Intent(this, Vehiculos::class.java)
            startActivity(intentinstrumentos)
        }
        binding.btnVehiculos.setOnClickListener {
            val intentRopadebebe = Intent(this, Vehiculos::class.java)
            startActivity(intentRopadebebe)
        }
        binding.btnVehiculos.setOnClickListener {
            val intentDeportes = Intent(this, Vehiculos::class.java)
            startActivity(intentDeportes)
        }
    }
}