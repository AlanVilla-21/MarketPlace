package com.example.marketplace

import android.content.Intent
import android.os.Bundle
import android.view.View
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

        //  binding del include del menú lateral
        val menuView: View = binding.root.findViewById(R.id.menuLateral)
         menuBinding = LayoutMenuLateralBinding.bind(menuView)

        // Abrir menú con ☰
        binding.txtMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        // ===== MENU LATERAL =====
        menuBinding.filaInicio.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, HomeUsuario::class.java))
        }

        menuBinding.filaVender.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, VenderProductoActivity::class.java))
        }

        menuBinding.filaCompras.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, HomeUsuario::class.java))
        }

        menuBinding.filaCategorias.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, Categorias_hambur::class.java))
        }

        menuBinding.filaPerfil.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, PerfilUsuario::class.java))
        }

        menuBinding.filaCerrarSesion.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, LoginUsuario::class.java))
            finish()
        }

        // ===== BARRA INFERIOR =====
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
