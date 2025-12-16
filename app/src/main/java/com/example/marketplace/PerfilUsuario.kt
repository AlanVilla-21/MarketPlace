package com.example.marketplace

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.marketplace.databinding.ActivityPerfilUsuarioBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase




class PerfilUsuario : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilUsuarioBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPerfilUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //nombre herencia
        auth = Firebase.auth
        val usuario = auth.currentUser
        val nombre = usuario?.displayName?:usuario?.email ?:"Anónimo"
        binding.Nombre.text = nombre
        //

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        binding.btnVenderProductos.setOnClickListener {
            startActivity(Intent(this, VenderProductos::class.java))
        }

        binding.btnCasa.setOnClickListener {
            val intentHomeProductos = Intent(this, HomeProductos::class.java)
            startActivity(intentHomeProductos)
        }
        binding.btnCarrito.setOnClickListener {
            val intentCarrito = Intent(this, Carrito::class.java)
            startActivity(intentCarrito)
        }
        binding.btnMiHistorial.setOnClickListener {
            val intentHistorial = Intent(this, HistorialCompras::class.java)
            startActivity(intentHistorial)
        }
        binding.btnMisCompras.setOnClickListener {
            val intentMisCompras = Intent(this, MisCompras::class.java)
            startActivity(intentMisCompras)
        }
        binding.btnVenderProductos.setOnClickListener {
            val intentVenderProductos = Intent(this, VenderProductos::class.java)
            startActivity(intentVenderProductos)
        }
    }
}