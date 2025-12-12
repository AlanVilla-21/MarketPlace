package com.example.marketplace
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.marketplace.databinding.ActivityMainBinding
import com.example.marketplace.databinding.LayoutMenuLateralBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var menuBinding: LayoutMenuLateralBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        menuBinding.filaInicio.setOnClickListener {
            startActivity(Intent(this, HomeUsuario::class.java))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        menuBinding.filaVender.setOnClickListener {
            startActivity(Intent(this, VenderProductoActivity::class.java))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        menuBinding.filaCompras.setOnClickListener {
            startActivity(Intent(this, HomeUsuario::class.java))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        menuBinding.filaCategorias.setOnClickListener {
            startActivity(Intent(this, categorias::class.java))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        menuBinding.filaPerfil.setOnClickListener {
            startActivity(Intent(this, PerfilUsuario::class.java))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        menuBinding.filaCerrarSesion.setOnClickListener {
            // volver al login y cerrar Main
            startActivity(Intent(this, LoginUsuario::class.java))
            finish()
        }
        binding.imgCasa.setOnClickListener {
            startActivity(Intent(this, HomeUsuario::class.java))
        }
        binding.imgCarrito.setOnClickListener {
            startActivity(Intent(this, HomeUsuario::class.java))
        }
        binding.imgPerfil.setOnClickListener {
            startActivity(Intent(this, PerfilUsuario::class.java))
        }
    }


    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}