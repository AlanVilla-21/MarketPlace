package com.example.marketplace

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.marketplace.databinding.ActivityRegisterUsuarioBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterUsuario : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterUsuarioBinding
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.btnContinuar.setOnClickListener {
            val correo = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            crearUsuario(correo, password)
        }

        binding.btnGoogle.setOnClickListener {
            //
        }
        }
    fun crearUsuario (
        correo:String,
        password: String
    ) {
        auth.createUserWithEmailAndPassword(correo, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Mi usuario se creo correctamente

            } else {
                //Nuestro usuario no se logio
                Toast.makeText(
                    baseContext,
                    "No pudo logearse el usuario.",
                    Toast.LENGTH_LONG,
                ).show()
            }
        }
    }
    }