package com.example.bitacoravirtual

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bitacoravirtual.databinding.ActivitySeleccionarEquipoBinding

class SeleccionarEquipo : AppCompatActivity() {
    lateinit var binding: ActivitySeleccionarEquipoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeleccionarEquipoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}