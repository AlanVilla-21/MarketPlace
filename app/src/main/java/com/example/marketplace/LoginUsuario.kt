package com.example.marketplace

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.marketplace.databinding.ActivityLoginUsuarioBinding
import com.example.marketplace.databinding.ActivityRegisterUsuarioBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView


class LoginUsuario : AppCompatActivity() {
    private lateinit var binding : ActivityLoginUsuarioBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_usuario)

        binding = ActivityLoginUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        var currentUser = auth.currentUser
        if (currentUser != null){
            //
            val intentUsuarioLogueado =  Intent(this, RecyclerView::class.java)
            startActivity(intentUsuarioLogueado)
        }


        binding.btnSignIn.setOnClickListener {
            //
            val correo = binding.etEmailLogin.text.toString()
            val password = binding.etPasswordLogin.text.toString()
            loginUsuario(correo, password)
        }

        binding.btnGoogleLogin.setOnClickListener {
            //
        }


    }
    fun loginUsuario (
        correo:String, password: String
    ){
        auth.signInWithEmailAndPassword(correo, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val intentUsuarioLogueado =  Intent(this, RegisterUsuario::class.java)
                startActivity(intentUsuarioLogueado)

                // Nuestro usuario se logio
            }else{
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
