package com.example.marketplace

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.marketplace.databinding.ActivityRegisterUsuarioBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase

class RegisterUsuario : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterUsuarioBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnContinuar.setOnClickListener {
            val correo = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Toast.makeText(this, "Correo inválido", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            crearUsuario(correo, password)
        }

        binding.btnYaTengoCuenta.setOnClickListener {
            val intentIrALogin = Intent(this, LoginUsuario::class.java)
            startActivity(intentIrALogin)
            finish()
        }
    }

    private fun crearUsuario(correo: String, password: String) {
        auth.createUserWithEmailAndPassword(correo, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intentUsuarioNuevo = Intent(this, HomeProductos::class.java)
                    startActivity(intentUsuarioNuevo)
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        task.exception?.message ?: "El usuario no pudo ser creado.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}
