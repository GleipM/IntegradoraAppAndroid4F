package com.example.bitacoravirtual

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.bitacoravirtual.databinding.ActivityRegistrarBinding

class RegistrarAlumno : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityRegistrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}