package com.example.marketplace

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.marketplace.databinding.ActivityMisComprasBinding

class MisCompras : AppCompatActivity() {
    private lateinit var binding: ActivityMisComprasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMisComprasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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
    }
}