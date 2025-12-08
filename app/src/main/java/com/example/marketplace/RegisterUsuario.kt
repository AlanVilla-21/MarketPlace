package com.example.marketplace

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.marketplace.databinding.ActivityRegisterUsuarioBinding

class RegisterUsuario : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterUsuarioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinuar.setOnClickListener {
            //
        }

        binding.btnGoogle.setOnClickListener {
            //
        }
        }
    }