package com.example.marketplace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LoginUsuario : AppCompatActivity() {
    private lateinit var binding : ActivityLoginUsuarioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_usuario)
        binding = ActivityLoginUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}