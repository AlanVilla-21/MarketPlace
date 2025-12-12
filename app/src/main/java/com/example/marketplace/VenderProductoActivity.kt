package com.example.marketplace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.marketplace.databinding.ActivityVenderProductoBinding

class VenderProductoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVenderProductoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVenderProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Flecha atrás
        binding.btnBackVender.setOnClickListener {
            finish()
        }

        // Publicar producto (por ahora solo leer campos)
        binding.btnPublicarProducto.setOnClickListener {
            val categoria = binding.etCategoria.text.toString()
            val descripcion = binding.etDescripcion.text.toString()
            val precio = binding.etPrecio.text.toString()

            // luego aquí guardas en DB / lista / etc.
        }
    }
}
