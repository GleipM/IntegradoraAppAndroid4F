package com.example.bitacoravirtual

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bitacoravirtual.databinding.ActivityAdminBitacoraBinding
import com.example.bitacoravirtual.databinding.ActivityMainBinding

class AdminBitacora : AppCompatActivity() {
    lateinit var binding: ActivityAdminBitacoraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBitacoraBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}