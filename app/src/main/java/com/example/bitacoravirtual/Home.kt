package com.example.bitacoravirtual

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.toolbox.Volley
import com.example.bitacoravirtual.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val correo = intent.getStringExtra("correo")

        binding.btnPerfil.setOnClickListener {
            val intent = Intent(this, Perfil::class.java)
            intent.putExtra("correo", correo)
            startActivity(intent)
        }
        binding.btnRegistrarBitacora.setOnClickListener {
            val intent = Intent(this, SeleccionarSalon::class.java)
            startActivity(intent)
        }

    }
}