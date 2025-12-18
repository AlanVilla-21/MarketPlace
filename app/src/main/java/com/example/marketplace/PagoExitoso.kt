package com.example.marketplace

import android.content.Intent
import android.os.Bundle
import com.example.marketplace.databinding.ActivityPagoExitosoBinding
import androidx.appcompat.app.AppCompatActivity

class PagoExitoso : AppCompatActivity() {

    private lateinit var binding: ActivityPagoExitosoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagoExitosoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvMensaje.text = "Compra realizada"

        binding.btnVolverHome.setOnClickListener {
            startActivity(Intent(this, HomeProductos::class.java))
            finish()
        }
    }
}
