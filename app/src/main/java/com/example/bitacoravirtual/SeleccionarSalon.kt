package com.example.bitacoravirtual

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bitacoravirtual.databinding.ActivitySeleccionarSalonBinding

class SeleccionarSalon : AppCompatActivity() {
    lateinit var binding: ActivitySeleccionarSalonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeleccionarSalonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSiguiente1.setOnClickListener {
            val intent = Intent(this, SeleccionarEquipo::class.java)
            startActivity(intent)
        }

    }
}