package com.example.marketplace

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.marketplace.databinding.ActivityLoginUsuarioBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginUsuario : AppCompatActivity() {

    private lateinit var binding : ActivityLoginUsuarioBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        // Si ya hay usuario logueado, lo mandamos directo al Home
//        val currentUser = auth.currentUser
//        if (currentUser != null){
//            val intentUsuarioLogueado =  Intent(this, HomeProductos::class.java)
//            startActivity(intentUsuarioLogueado)
//            finish()
//        }

        binding.btnSignIn.setOnClickListener {
            val correo = binding.etEmailLogin.text.toString()
            val password = binding.etPasswordLogin.text.toString()
            loginUsuario(correo, password)
        }

        binding.btnGoogleLogin.setOnClickListener {
            // Luego Google
        }
    }

    private fun loginUsuario(correo:String, password: String) {
        auth.signInWithEmailAndPassword(correo, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val intentUsuarioLogueado =  Intent(this, HomeProductos::class.java)
                    startActivity(intentUsuarioLogueado)
                    finish()
                }else{
                    Toast.makeText(
                        baseContext,
                        "No pudo logearse el usuario.",
                        Toast.LENGTH_LONG,
                    ).show()
                }
            }
    }

}
