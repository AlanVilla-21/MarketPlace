package com.example.marketplace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.marketplace.databinding.ActivityRegisterUsuarioBinding

class RegisterUsuario : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ejemplo: botón continuar/registrar
        binding.btnContinuar.setOnClickListener {
            // lógica de registrar (firebase)
        }

        // Ejemplo: ir al login
        binding.btnYaTengoCuenta.setOnClickListener {
            finish() // o Intent a LoginUsuario
        }
    }
}
