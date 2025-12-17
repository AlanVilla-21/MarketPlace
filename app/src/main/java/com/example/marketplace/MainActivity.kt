package com.example.marketplace

import android.os.Bundle
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.marketplace.R

class MainActivity : AppCompatActivity (){
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}