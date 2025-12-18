package com.example.marketplace

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.marketplace.databinding.ActivityLoginUsuarioBinding
import com.example.marketplace.dataclases.UsuarioGuardado
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class LoginUsuario : AppCompatActivity() {

    private lateinit var binding: ActivityLoginUsuarioBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var sharedPreferences: SharedPreferences
    private val context: Context = this

    companion object {
        val NOMBRE_FICHERO_SHARED_PREFERENCES = "MarketPlacePrefs"
        val USUARIO_GUARDADO = "UsuarioGuardadoJson"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        sharedPreferences = context.getSharedPreferences(
            NOMBRE_FICHERO_SHARED_PREFERENCES, MODE_PRIVATE
        )

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, HomeProductos::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            return
        }

        binding.btnSignIn.setOnClickListener {
            val correo = binding.etEmailLogin.text.toString()
            val password = binding.etPasswordLogin.text.toString()

            if (correo.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa correo y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginUsuario(correo, password)
        }

        binding.btnCrearCuenta.setOnClickListener {
            startActivity(Intent(this, RegisterUsuario::class.java))
        }
    }

    private fun loginUsuario(correo: String, password: String) {
        auth.signInWithEmailAndPassword(correo, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val uid = auth.currentUser?.uid ?: ""
                    val usuario = UsuarioGuardado(uid, correo)
                    val usuarioString = Json.encodeToString(usuario)

                    guardarDatosSharedPreferences(
                        USUARIO_GUARDADO,
                        usuarioString
                    )

                    val intent = Intent(this, HomeProductos::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(
                        this,
                        task.exception?.localizedMessage ?: "No pudo logearse el usuario.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun guardarDatosSharedPreferences(nombreDelDato: String, datoAGuardar: String) {
        val editor = sharedPreferences.edit()
        editor.putString(nombreDelDato, datoAGuardar)
        editor.apply()
    }

    fun obtenerDatosSharedPreferences(nombreDelDato: String): String? {
        return sharedPreferences.getString(nombreDelDato, "No Existe el Dato almacenado")
    }
}
