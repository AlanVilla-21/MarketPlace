package com.example.marketplace

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import android.content.Intent
import com.example.marketplace.databinding.ActivityPagoTarjetaBinding

class PagoTarjeta : AppCompatActivity() {
    private lateinit var binding: ActivityPagoTarjetaBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPagoTarjetaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val total = intent.getDoubleExtra("total", 0.0)
        binding.tvTotal.text = "Total: Bs. $total"

        binding.btnPagar.setOnClickListener {
            val numero = binding.etNumeroTarjeta.text.toString().trim()
            val expiracion = binding.etExpiracion.text.toString().trim()
            val cvv = binding.etCvv.text.toString().trim()
            val total = intent.getDoubleExtra("total", 0.0)
            if (numero.isEmpty() || expiracion.isEmpty() || cvv.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (numero.length < 13 || numero.length > 19) {
                Toast.makeText(this, "Número de tarjeta inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (cvv.length < 3||cvv.length>4){
                Toast.makeText(this, "CVV inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            if (!expiracion.matches(Regex("\\d{2}/\\d{2}"))){
                Toast.makeText(this, "Fecha de expiración inválida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val  i = Intent(this, PagoExitoso::class.java)
            i.putExtra("total", total)
            startActivity(i)
            finish()
        }
        binding.btnCancelar.setOnClickListener {
            finish()
        }

    }
}
